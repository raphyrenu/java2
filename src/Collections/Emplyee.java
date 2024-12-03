package Collections;

public class Emplyee  implements Comparable{
    private Integer id;
    private String FirstName;
    private String Lastname;
    private String Institution;
    private String Position;
    private  Integer Salary;

    public Emplyee() {
    }

    public Emplyee(int id, String firstName, String lastname, String institution, String position, int salary) {
        this.id = id;
        FirstName = firstName;
        Lastname = lastname;
        Institution = institution;
        Position = position;
        Salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getInstitution() {
        return Institution;
    }

    public void setInstitution(String institution) {
        Institution = institution;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    @Override
    public String toString() {
        return "Emplyee{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Institution='" + Institution + '\'' +
                ", Position='" + Position + '\'' +
                ", Salary=" + Salary +
                '}';
    }
    @Override
    public boolean equals(Object o){
        Emplyee emp = (Emplyee) o;
        return this.id.equals(emp.id) &&this.FirstName.equals(emp.FirstName)&& this.Lastname.equals(emp.Lastname)&&this.Institution.equals(emp.Institution)&&this.Position.equals(emp.Position)&&this.Salary.equals(emp.Salary);
    }
    @Override
    public int hashCode(){
        return this.id.hashCode()+this.FirstName.hashCode()+this.Lastname.hashCode()+this.Institution.hashCode()+this.Position.hashCode()+this.Salary.hashCode();
    }

    @Override
    public int compareTo(Object o) {

        Emplyee emp = (Emplyee) o;
        if(this.id.compareTo(emp.id)!=0){
            return this.id.compareTo(emp.id);
    }
        if(this.FirstName.compareTo(emp.FirstName)!=0){
            return this.FirstName.compareTo(emp.FirstName);
        }
        if(this.Lastname.compareTo(emp.Lastname)!=0){
            return this.Lastname.compareTo(emp.Lastname);
        }
        if(this.Institution.compareTo(emp.Institution)!=0){
            return this.Institution.compareTo(emp.Institution);
        }
        if(this.Position.compareTo(emp.Position)!=0){
            return this.Position.compareTo(emp.Position);
        }

        return this.Salary.compareTo(emp.Salary);


    }
}
