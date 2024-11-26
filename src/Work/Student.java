package Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student {
    private int personId; // Foreign key to link to the Person
    private String combination;
    private String level;
    private String studentClass;
    private String school;

    public Student(int personId, String combination, String level, String studentClass, String school) {
        this.personId = personId;
        this.combination = combination;
        this.level = level;
        this.studentClass = studentClass;
        this.school = school;
    }

    // Getters and Setters...

    public void saveToDB(Connection conn) throws SQLException {
        String query = "INSERT INTO students (person_id, combination, level, studentClass, school) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, this.personId);       // Set the person ID
        stmt.setString(2, this.combination);  // Set the combination
        stmt.setString(3, this.level);        // Set the level
        stmt.setString(4, this.studentClass); // Set the student class
        stmt.setString(5, this.school);       // Set the school

        stmt.executeUpdate();
        System.out.println("Student data saved to DB for person ID: " + this.personId);
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
