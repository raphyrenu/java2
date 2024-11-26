package Work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/school"; // Change database name if needed
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            boolean running = true;

            while (running) {
                System.out.println("What would you like to do?");
                System.out.println("1. Add a new student");
                System.out.println("2. View all students");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1/2/3): ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addStudent(scanner, conn);
                        break;
                    case 2:
                        DataRetriever.retrieveAndDisplayData(conn);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } finally {
            scanner.close();
        }
    }

    private static void addStudent(Scanner scanner, Connection conn) throws SQLException {
        // Input for Person
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter age:");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter gender:");
        String gender = scanner.nextLine();

        // Create and save Person
        Person person = new Person(firstName, lastName, age, gender);
        person.saveToDB(conn);

        // Input for Student
        System.out.println("Enter combination:");
        String combination = scanner.nextLine();
        System.out.println("Enter level:");
        String level = scanner.nextLine();
        System.out.println("Enter class:");
        String studentClass = scanner.nextLine();
        System.out.println("Enter school:");
        String school = scanner.nextLine();

        // Create and save Student
        Student student = new Student(person.getId(), combination, level, studentClass, school);
        student.saveToDB(conn);
    }
}
