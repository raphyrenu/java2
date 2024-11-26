package Exception;

public class ThrowDemo {

    static void demoproc( String message) throws IllegalAccessException {
        try{
            if (message==null)
                throw new IllegalAccessException("demo");
        }
        catch (IllegalAccessException e){
            System.out.println("Caught inside demoproc");
            throw e;



    }}

    public static void main(String[] args) {
        try {
            String name="null";
            demoproc(name);
        }
        catch (IllegalAccessException e){
            System.out.println("message : " +e);

        }

    }
}
