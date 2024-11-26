package Collections;

import java.util.ArrayList;

public class Arthmetics {
  Object addition (ArrayList<?> numbers){
        Object sum = null;
        for (Object o : numbers) {
            int a = (Integer) o;
             sum += o.toString();
        }

        return  sum;
    }
}
