import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DB {
    // Database URL, Username, and Password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        try {
            // Establish the connection
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the user table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS user ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(50), "
                    + "age INT, "
                    + "Address VARCHAR(70), "
                    + "email VARCHAR(100),"
                    + "PRIMARY KEY (id))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'user' created successfully.");

            // Prompt user for data
            System.out.print("Enter name: ");
            String name = scanner.nextLine();  // Read user input for name

            System.out.print("Enter age: ");
            int age = scanner.nextInt();  // Read user input for age
            scanner.nextLine(); // Consume newline left-over

            System.out.print("Enter address: ");
            String address = scanner.nextLine();  // Read user input for address

            System.out.print("Enter email: ");
            String email = scanner.nextLine();  // Read user input for email

            // Insert user input into the table
            String insertSQL = "INSERT INTO user (name, age, Address, email) VALUES ('" + name + "', " + age + ", '" + address + "', '" + email + "')";
            stmt.executeUpdate(insertSQL);
            System.out.println("Inserted values into 'user'.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                scanner.close(); // Close the scanner
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
