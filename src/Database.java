import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student"; // Static database URL
    private static final String USER = "root"; // Static username
    private static final String PASSWORD = ""; // Static password (modify as needed)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for operation type
        System.out.print("Do you want to (d)elete or (u)pdate a user? (d/u): ");
        String operation = scanner.nextLine();

        // Initialize connection and statement objects
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();

            if (operation.equalsIgnoreCase("d")) {
                // Prompt for user deletion
                System.out.print("Enter the name of the user to delete: ");
                String nameToDelete = scanner.nextLine();
                stmt.executeUpdate("DELETE FROM user WHERE name = '" + nameToDelete + "'");
                System.out.println("User " + nameToDelete + " deleted successfully.");
            } else if (operation.equalsIgnoreCase("u")) {
                // Prompt for user update
                System.out.print("Enter the name of the user to update: ");
                String oldName = scanner.nextLine();
                System.out.print("Enter the new name for the user: ");
                String newName = scanner.nextLine();
                stmt.executeUpdate("UPDATE user SET name = '" + newName + "' WHERE name = '" + oldName + "'");
                System.out.println("User " + oldName + " updated to " + newName + " successfully.");
            } else {
                System.out.println("Invalid operation. Please choose 'd' to delete or 'u' to update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
