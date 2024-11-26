package Collections;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        Arthmetics a = new Arthmetics();

        ArrayList<Integer> arr = new ArrayList<Integer>();
 Box<Integer> b1 = new Box<Integer>(10,34,67);
        Box<Double> b2 = new Box<Double>(10.5,34.9,67.5);
        arr.add(100);
        arr.add(200);
        arr.add(300);
        System.out.println(a.addition(arr));
        System.out.println(b2.getWidth());


        Set<Emplyee> empo = new TreeSet<Emplyee>();

        empo.add(new Emplyee(1,"Mary","Mugisha","Rca","Accountant", 100));
        empo.add(new Emplyee(2,"Mike","Manzi","Rca","Instructor",1200));
        empo.add(new Emplyee(3,"Peter","Ganza","RAA","Dev",11500));
        empo.add(new Emplyee(2,"Mike","Manzi","Rca","Instructor",1200));
        empo.add(new Emplyee(2,"Ben","Manzi","Rca","Instructor",1200));

//Collections.sort(empo);

        for (Emplyee emp : empo){
            System.out.println(emp.toString());
        }
    }
}
