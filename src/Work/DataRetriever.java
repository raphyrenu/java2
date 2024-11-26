package Work;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataRetriever {
    public static void retrieveAndDisplayData(Connection conn) {
        String query = "SELECT p.firstName, p.lastName, p.age, p.gender, s.combination, s.level, s.studentClass, s.school " +
                "FROM persons p " +
                "JOIN students s ON p.id = s.person_id";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nRetrieved Data:");
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String combination = rs.getString("combination");
                String level = rs.getString("level");
                String studentClass = rs.getString("studentClass");
                String school = rs.getString("school");

                System.out.printf("Name: %s %s, Age: %d, Gender: %s, Combination: %s, Level: %s, Class: %s, School: %s%n",
                        firstName, lastName, age, gender, combination, level, studentClass, school);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
