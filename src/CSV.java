import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSV {

    // MySQL connection details
    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\RAPHAEL\\Documents\\database\\src\\school.csv"; // Path to the CSV file
        String line;
        String csvSplitBy = ",";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Connect to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "INSERT INTO students (student_name, subject, score) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // Skip the header
            br.readLine();

            // Read and process each line
            while ((line = br.readLine()) != null) {
                // Split line by commas
                String[] data = line.split(csvSplitBy);

                String studentName = data[0];
                String subject = data[1];
                int score = Integer.parseInt(data[2]);

                // Set parameters and execute insert statement
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, subject);
                preparedStatement.setInt(3, score);
                preparedStatement.executeUpdate();
            }

            System.out.println("Data has been successfully inserted into the MySQL database.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database or executing the query: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing the database resources: " + e.getMessage());
            }
        }
    }
}
