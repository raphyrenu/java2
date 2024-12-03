package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
    public static void main(String[] args) {


        Pattern pattern1 = Pattern.compile("x*");

        // Matcher to find matches in the string "examples of xavi"
        Matcher matcher = pattern1.matcher("examples of xavi");
        


            String group = matcher.group().replace("x", "k");
            System.out.println(group);

    }
}
