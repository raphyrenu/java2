//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.sql.SQLException;
//
//public class Main {
//
//    // Database URL, Username, and Password
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
//    private static final String USER = "root";
//    private static final String PASS = "";
//
//    public static void main(String[] args) {
//        Connection conn = null;
//        Statement stmt = null;
//
//        try {
//            // Establish the connection
//            System.out.println("Connecting to the database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            // Create a statement and execute a query
//            stmt = conn.createStatement();
//            String sql = "SELECT id, address, email FROM user";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            // Iterate through the result set
//            while (rs.next()) {
//                // Retrieve data by column name
//                int id = rs.getInt("id");
//                String address = rs.getString("address");
//                String email = rs.getString("email");
//
//                // Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Address: " + address);
//                System.out.println(", Email: " + email);
//            }
//
//            // Close the ResultSet
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Close resources
//            try {
//                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//    }
//}
