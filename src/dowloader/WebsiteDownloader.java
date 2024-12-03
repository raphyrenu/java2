package dowloader;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Connection;
import java.time.*;
import java.util.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class WebsiteDownloader {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/downloader";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final String URL_REGEX = "^(https?://)?([\\w.-]+)+(:\\d+)?(/.*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the website URL to download:");
        String websiteUrl = scanner.nextLine();

        if (!isValidUrl(websiteUrl)) {
            System.out.println("Invalid URL. Please enter a valid URL.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Step 1: Extract domain name and create directory
            String domainName = new URL(websiteUrl).getHost();
            File websiteDir = new File(domainName);
            if (!websiteDir.exists()) websiteDir.mkdir();

            System.out.println("Starting download for: " + domainName);
            long startTime = System.currentTimeMillis();

            long totalDownloadedBytes = 0;

            // Step 2: Download the homepage
            File homePage = new File(websiteDir, "index.html");
            downloadFile(websiteUrl, homePage);
            long homePageSize = homePage.length();
            totalDownloadedBytes += homePageSize;
            System.out.println("Downloaded homepage to " + homePage.getAbsolutePath() + " (" + homePageSize / 1024 + " KB)");

            // Step 3: Extract external links using regular expressions
            List<String> externalLinks = extractLinks(homePage);
            saveWebsiteToDB(connection, domainName, startTime, System.currentTimeMillis(), 0, 0);

            int websiteId = getWebsiteId(connection, domainName);

            for (String link : externalLinks) {
                System.out.println("Found link: " + link);
                saveLinkToDB(connection, websiteId, link);
            }

            // Step 4: Download external links
            for (String link : externalLinks) {
                try {
                    String fileName = generateUniqueFileName(link, websiteDir);
                    File linkFile = new File(websiteDir, fileName);
                    long linkStartTime = System.currentTimeMillis();
                    downloadFile(link, linkFile);
                    long elapsed = System.currentTimeMillis() - linkStartTime;
                    long fileSize = linkFile.length();
                    totalDownloadedBytes += fileSize;
                    updateLinkInDB(connection, websiteId, link, elapsed, fileSize / 1024);
                    System.out.printf("Downloaded %s (%d KB in %d ms)\n", link, fileSize / 1024, elapsed);
                } catch (Exception e) {
                    System.out.println("Failed to download link: " + link);
                }
            }

            long totalElapsed = System.currentTimeMillis() - startTime;
            long totalDownloadedKilobytes = totalDownloadedBytes / 1024;
            System.out.println("Download complete in " + totalElapsed + " ms, Total size: " + totalDownloadedKilobytes + " KB");
            updateWebsiteInDB(connection, domainName, startTime, System.currentTimeMillis(), totalElapsed, totalDownloadedKilobytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Validate URL using regex
    private static boolean isValidUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    private static void downloadFile(String url, File file) throws IOException {
        URL website = new URL(url);
        try (InputStream in = website.openStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        if (!file.exists() || file.length() == 0) {
            throw new IOException("File download failed or file is empty for URL: " + url);
        }
    }

    // Extract links using regex within HTML content
    private static List<String> extractLinks(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");
        Elements links = doc.select("a[href]");
        List<String> externalLinks = new ArrayList<>();
        for (Element link : links) {
            String href = link.attr("abs:href");
            if (isValidUrl(href)) {
                externalLinks.add(href);
            }
        }
        return externalLinks;
    }

    private static String generateUniqueFileName(String url, File directory) throws MalformedURLException {
        String path = new URL(url).getPath();
        String fileName = path.isEmpty() || path.equals("/") ? "index.html" : path.substring(path.lastIndexOf('/') + 1);
        File file = new File(directory, fileName);
        int count = 1;
        while (file.exists()) {
            String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
            String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";
            fileName = baseName + "_" + count + extension;
            file = new File(directory, fileName);
            count++;
        }
        return fileName;
    }

    private static void saveWebsiteToDB(Connection conn, String name, long start, long end, long elapsed, long size) throws SQLException {
        String sql = "INSERT INTO websites (website_name, download_start_date_time, download_end_date_time, total_elapsed_time, total_downloaded_kilobytes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setTimestamp(2, new Timestamp(start));
            stmt.setTimestamp(3, new Timestamp(end));
            stmt.setLong(4, elapsed);
            stmt.setLong(5, size);
            stmt.executeUpdate();
        }
    }

    private static void saveLinkToDB(Connection conn, int websiteId, String link) throws SQLException {
        String sql = "INSERT INTO links (link_name, website_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, link);
            stmt.setInt(2, websiteId);
            stmt.executeUpdate();
        }
    }

    private static void updateLinkInDB(Connection conn, int websiteId, String link, long elapsed, long size) throws SQLException {
        String sql = "UPDATE links SET total_elapsed_time = ?, total_downloaded_kilobytes = ? WHERE link_name = ? AND website_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, elapsed);
            stmt.setLong(2, size);
            stmt.setString(3, link);
            stmt.setInt(4, websiteId);
            stmt.executeUpdate();
        }
    }

    private static void updateWebsiteInDB(Connection conn, String name, long start, long end, long elapsed, long totalSize) throws SQLException {
        String sql = "UPDATE websites SET download_start_date_time = ?, download_end_date_time = ?, total_elapsed_time = ?, total_downloaded_kilobytes = ? WHERE website_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(start));
            stmt.setTimestamp(2, new Timestamp(end));
            stmt.setLong(3, elapsed);
            stmt.setLong(4, totalSize);
            stmt.setString(5, name);
            stmt.executeUpdate();
        }
    }

    private static int getWebsiteId(Connection conn, String name) throws SQLException {
        String sql = "SELECT id FROM websites WHERE website_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Website not found in database.");
            }
        }
    }
}
