package Exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RwandaDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/rwanda"; // Database URL
    private static final String USER = "root"; // Replace with your database username
    private static final String PASS = ""; // Replace with your database password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Insert data in the correct order to satisfy foreign key constraints
            insertDataFromFile(conn, "Province.txt", "INSERT INTO Province (provinceid, provincename) VALUES (?, ?)");
            insertDataFromFile(conn, "District.txt", "INSERT INTO District (districtid, provinceid, districtname) VALUES (?, ?, ?)");
            insertDataFromFile(conn, "Sector.txt", "INSERT INTO Sector (sectorid, districtid, sectorname) VALUES (?, ?, ?)");
            insertDataFromFile(conn, "Cell.txt", "INSERT INTO Cell (cellid, sectorid, cellname) VALUES (?, ?, ?)");
            insertDataFromFile(conn, "Village.txt", "INSERT INTO Village (villageid, cellid, villagename) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertDataFromFile(Connection conn, String filePath, String sql) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                for (int i = 0; i < data.length; i++) {
                    pstmt.setString(i + 1, data[i].trim());
                }
                pstmt.executeUpdate(); // Execute the insert statement
            }
        } catch (IOException e) {
            System.err.println("IOException occurred while reading file: " + filePath);
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while inserting data from file: " + filePath);
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
