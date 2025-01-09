package dowloader;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class WebsiteDownloader {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/downloader";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final int MAX_RETRIES = 3;

    // Regular expression for validating URLs
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|http)://[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*(\\\\.[a-zA-Z]{2,})(:[0-9]{1,5})?(/.*)?$");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the website URL to download:");
        String websiteUrl = scanner.nextLine();

        if (!isValidUrl(websiteUrl)) {
            System.out.println("Invalid URL. Please enter a valid URL.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Extract domain name from URL
            String domainName = new URL(websiteUrl).getHost();
            long startTime = System.currentTimeMillis();

            // Initialize the website record in the database
            int websiteId = insertWebsite(connection, domainName, startTime);

            // Create a directory for storing the website files
            File websiteDir = new File(domainName);
            if (!websiteDir.exists()) websiteDir.mkdir();

            System.out.println("Starting download for: " + domainName);
            long totalDownloadedBytes = 0;

            Queue<String> linksToVisit = new LinkedList<>();
            Set<String> visitedLinks = new HashSet<>();
            linksToVisit.add(websiteUrl);
            visitedLinks.add(websiteUrl);

            while (!linksToVisit.isEmpty()) {
                String currentLink = linksToVisit.poll();
                try {
                    // Record start time for the link
                    long linkStartTime = System.currentTimeMillis();

                    // Download the link and save it to a file
                    File file = downloadLink(currentLink, websiteDir);
                    long fileSize = file.length();
                    totalDownloadedBytes += fileSize;

                    // Record end time and calculate elapsed time
                    long linkEndTime = System.currentTimeMillis();
                    long linkElapsedTime = linkEndTime - linkStartTime;

                    System.out.printf("Downloaded: %s (%d KB, elapsed time: %d ms)%n", currentLink, fileSize / 1024, linkElapsedTime);

                    // Insert the link record into the database
                    insertLink(connection, websiteId, currentLink, fileSize, linkElapsedTime);

                    // Extract additional links from the file
                    Set<String> newLinks = extractLinks(file, websiteUrl);
                    for (String link : newLinks) {
                        if (!visitedLinks.contains(link)) {
                            linksToVisit.add(link);
                            visitedLinks.add(link);
                        }
                    }
                } catch (Exception e) {
                    System.out.printf("Failed to process link: %s (%s)%n", currentLink, e.getMessage());
                }
            }

            // Update the website record in the database
            long endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;
            updateWebsite(connection, websiteId, startTime, endTime, elapsed, totalDownloadedBytes);

            System.out.printf("Download complete. Total size: %d KB, elapsed time: %d ms%n",
                    totalDownloadedBytes / 1024, elapsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper to validate URLs using regex pattern
    private static boolean isValidUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    // Insert a new website into the database
    private static int insertWebsite(Connection conn, String websiteName, long startTime) throws SQLException {
        String sql = "INSERT INTO websites (website_name, download_start_date_time, download_end_date_time, total_elapsed_time, total_downloaded_kilobytes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, websiteName);
            stmt.setTimestamp(2, new Timestamp(startTime));
            stmt.setTimestamp(3, new Timestamp(startTime)); // Placeholder, updated later
            stmt.setLong(4, 0); // Placeholder, updated later
            stmt.setLong(5, 0); // Placeholder, updated later
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                throw new SQLException("Failed to insert website record.");
            }
        }
    }

    // Insert a link into the database
    private static void insertLink(Connection conn, int websiteId, String link, long size, long elapsedTime) throws SQLException {
        String sql = "INSERT INTO links (link_name, website_id, total_elapsed_time, total_downloaded_kilobytes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, link);
            stmt.setInt(2, websiteId);
            stmt.setLong(3, elapsedTime); // Store elapsed time
            stmt.setLong(4, size / 1024); // Convert bytes to kilobytes
            stmt.executeUpdate();
        }
    }

    // Update website details after download is complete
    private static void updateWebsite(Connection conn, int websiteId, long startTime, long endTime, long elapsed, long size) throws SQLException {
        String sql = "UPDATE websites SET download_end_date_time = ?, total_elapsed_time = ?, total_downloaded_kilobytes = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(endTime));
            stmt.setLong(2, elapsed);
            stmt.setLong(3, size / 1024); // Convert bytes to kilobytes
            stmt.setInt(4, websiteId);
            stmt.executeUpdate();
        }
    }

    // Download a link and save it to a file
    private static File downloadLink(String url, File directory) throws IOException {
        String fileName = sanitizeFileName(url);
        File file = new File(directory, fileName + ".html");
        for (int i = 0; i < MAX_RETRIES; i++) {
            try (InputStream in = new URL(url).openStream(); FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                return file;
            } catch (IOException e) {
                System.out.printf("Retrying (%d/%d) for: %s%n", i + 1, MAX_RETRIES, url);
            }
        }
        throw new IOException("Failed to download: " + url);
    }

    // Extract links from a downloaded file
    private static Set<String> extractLinks(File file, String baseUrl) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8", baseUrl);
        Set<String> links = new HashSet<>();
        for (Element link : doc.select("a[href]")) {
            String href = link.absUrl("href");
            if (href.startsWith(baseUrl)) {
                links.add(href);
            }
        }
        return links;
    }


    private static String sanitizeFileName(String url) {
        String sanitized = url.replaceAll("[^a-zA-Z0-9.-]", "_");
        Matcher matcher = Pattern.compile("^(.*?)([?#].*)?$").matcher(sanitized);
        if (matcher.matches()) {
            return matcher.group(1);  // Return the main part before any query string or fragment
        }
        return sanitized;  // Return sanitized name if no match
    }
}
