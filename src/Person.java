

// Person class
class Person {
    // Attributes of the Person class
    private String name;
    private int age;

    // Constructor of the Person class
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge(int i) {
        return age;
    }

    // Method to display Person details
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

// Student class inheriting from Person
class Student extends Person {
    // Additional attribute for Student
    private String studentID;

    // Constructor for Student, using super to call the parent class constructor
    public Student(String name, int age, String studentID) {
        super(name, age); // Calling the Person constructor
        this.studentID = studentID;
    }

    // Getter method for studentID
    public String getStudentID() {
        return studentID;
    }

    // Method to display Student details, including Person details
    @Override
    public void displayInfo() {
        super.displayInfo(); // Displaying Person's details
        System.out.println("Student ID: " + studentID);
    }
}

// S1Student class inheriting from Student
class S1Student extends Student {
    // Additional attribute for S1Student
    private String major;

    // Constructor for S1Student, using super to call the parent class constructor
    public S1Student(String name, int age, String studentID, String major) {
        super(name, age, studentID); // Calling the Student constructor
        this.major = major;
    }

    // Getter method for major
    public String getMajor() {
        return major;
    }

    // Method to display S1Student details, including Student and Person details
    @Override
    public void displayInfo() {
        super.displayInfo(); // Displaying Student's details
        System.out.println("Major: " + major);
    }
}

// Main class to test the hierarchy
class Main {
    public static void main(String[] args) {
        // Creating an S1Student object
      Person s1 = new Person("raphael",23);
         s1.getAge(20);
        // Displaying S1Student information
        s1.displayInfo();
    }
}
