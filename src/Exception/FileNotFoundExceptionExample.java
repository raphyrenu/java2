package Exception;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileNotFoundExceptionExample {
    public static void main(String[] args) throws IOException {
try {


            BufferedReader br = new BufferedReader(new FileReader("District.txt"));
            String data = null;
            while ((data = br.readLine()) != null){
                System.out.println(data);
            }

}
catch (IOException e){
    System.out.println(e);
}
    }

}
