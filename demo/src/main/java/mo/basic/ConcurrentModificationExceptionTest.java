package main.java.mo.basic;

import java.util.ArrayList;

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

        for (String string : strings) {
            if ("e".equals(string)) {
                strings.remove(string);
            }
        }
    }
}
