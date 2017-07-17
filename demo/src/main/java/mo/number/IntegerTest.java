package main.java.mo.number;

import main.java.mo.sy.ObjectC;

/**
 * Created by M on 17/7/17.
 */
public class IntegerTest {
    public static void main(String[] args) {
        Integer integer1 = new Integer(1);
        Integer integer2 = new Integer(1);

        Integer integer3 = Integer.valueOf(1);
        Integer integer4 = Integer.valueOf(1);
        Integer integer5 = Integer.valueOf(10000);

        System.out.println(integer1 == integer3);
        System.out.println(integer4 == integer3);
        System.out.println(integer5 == integer3);

        System.out.println(integer1.hashCode());
        System.out.println(integer2.hashCode());
        System.out.println(integer3.hashCode());

    }


}
