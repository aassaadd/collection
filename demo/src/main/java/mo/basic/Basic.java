package main.java.mo.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MoXingwang on 2017/6/30.
 */
public class Basic {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] b = new int[]{1,2,3,4,5,6};
        int[] c = new int[6];

        for (int i = 0; i < a.length; i++) {
            System.out.println(i);
        }
        for (int i : a) {
            System.out.println(i);
        }

        System.arraycopy(a,1,c,2,2);

        System.out.println(a);
        System.out.println(c.toString());


        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        for (int i = 0; i < list.size(); i++) {
            
        }

        for (Integer integer : list) {
            
        }

        Iterator<Integer> integerIterator =  list.iterator();
        while (integerIterator.hasNext()){
            System.out.println(integerIterator.next());
        }

        Integer[] tt = new Integer[]{1,1,1,1,1,1,1};

        Integer[] integers = (Integer[]) list.toArray(tt);


    }
}

