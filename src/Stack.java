
public class Stack {


    public void printMe(){

        System.out.println("Hello world");


    }

    public static void main(String[] args) {
//        Stack s= new Stack();
//        s.printMe();
//       Person p = new Person();
//       p.setName(null);
//        System.out.println(p.getName());

        try {
            int a = 50;
            int b = 0;
            int c = a / b;
            System.out.println("The result is " + c);
            int[] numbers = {1, 3, 4, 5};
            System.out.println(numbers[10]);

        }

        catch (ArithmeticException exc){
            System.out.println("Division by zero " + exc.getMessage());
        }
//        catch (ArrayIndexOutOfBoundsException exc){
//            System.out.println("Sorry");
//        }
//        finally {
//            System.out.println("Finally, you got it");
//        }

    }
}
