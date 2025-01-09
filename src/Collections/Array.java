package Collections;

import java.util.*;


public class Array {
    public static void main(String[] args) {
      Set items =new TreeSet();
        items.add(101);
        items.add("Nelson");
        items.add(3.14);
        items.add(new Date());

       for(Object p :items){
           System.out.println(p);
       }
//       Map map = new HashMap();
//       map.put("username", "Nelson");
//       map.put("password",123);
//        System.out.println(map.get("username"));
//
//List<Integer> nums = new ArrayList<Integer>();


    }
}
