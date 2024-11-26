package Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private int id; // Holds the generated ID

    public Person(String firstName, String lastName, int age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    // Getters and Setters...

    public int getId() {
        return id;
    }

    public void saveToDB(Connection conn) throws SQLException {
        String query = "INSERT INTO persons (firstName, lastName, age, gender) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, this.firstName);
        stmt.setString(2, this.lastName);
        stmt.setInt(3, this.age);
        stmt.setString(4, this.gender);
        stmt.executeUpdate();

        // Retrieve the generated ID
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1); // Set the generated ID
        }

        System.out.println("Person data saved to DB with ID: " + this.id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }
}
