
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    public class Users {
        // Database URL, Username, and Password
        private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
        private static final String USER = "root";
        private static final String PASS = "";

        public static void main(String[] args) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            FileWriter csvWriter = null;

            try {
                // Establish the connection
                System.out.println("Connecting to the database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                // SQL query to retrieve data from the 'user' table
                String query = "SELECT * FROM user";
                rs = stmt.executeQuery(query);

                // Create a CSV file
                csvWriter = new FileWriter("users.csv");

                // Write the CSV header
                csvWriter.append("ID,Name,Age,Address,Email\n");

                // Write data to CSV file
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String address = rs.getString("Address");
                    String email = rs.getString("email");

                    // Write data to CSV
                    csvWriter.append(id + "," + name + "," + age + "," + address + "," + email + "\n");
                }

                System.out.println("Data has been successfully exported to users.csv.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error writing to CSV file: " + e.getMessage());
            } finally {
                // Close the resources
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                    if (csvWriter != null) csvWriter.close();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


