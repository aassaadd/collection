package main.java.mo.basic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by MoXingwang on 2017/7/2.
 */
public class ConcurrentModificationExceptionTest {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");

        ArrayList<String> tempStrings = new ArrayList<String>();
        for (String string : strings) {
            if("e".equals(string)){
                tempStrings.add(string);
            }
        }
        strings.removeAll(tempStrings);



        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String element = iterator.next();
            if("e".equals(element)){
                iterator.remove();
            }
        }

        for (int i = 0; i < strings.size(); i++) {
            String element = strings.get(i);
            if("d".equals(element)){
                strings.remove(element);
                i --;//需要自己手动维护索引
            }
        }

        System.out.println("");

        /*for (String string : strings) {
            if ("e".equals(string)) {
                strings.remove(string);
            }
        }*/
    }
}
