package main.java.mo.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MoXingwang on 2017/6/30.
 */
public class Basic {
    public static void main(String[] args) {
        List<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");

        for (String string : strings) {
            strings.remove("e");
        }








        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);

        LinkedList<Integer> linkedList2 = new LinkedList<Integer>();
        linkedList2.add(11);
        linkedList2.add(22);
        linkedList2.add(3);
        linkedList2.add(9);
//        linkedList2.add(8);


        /*for (int i = 0; i < linkedList.size(); i++) {
            for (int i1 = 0; i1 < linkedList2.size(); i1++) {
                if(linkedList.get(i).equals(linkedList2.get(i1))){
                    linkedList2.remove(i1);
//                    i1 --;
                }
            }
        }*/

        for (Integer integer : linkedList) {
            for (Integer integer1 : linkedList2) {
                if(integer1.equals(integer)) {
                    linkedList2.remove(integer1);
                }
            }
            /*for(Iterator<Integer> iterator = linkedList2.iterator();iterator.hasNext();){
                if(iterator.next().equals(integer)){
                    iterator.remove();
                }
            }*/
        }




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

        list.add(null);
        list.add(null);
        list.add(null);

        System.out.println(list.indexOf(null));

        List<Integer> temp = list.subList(1,2);

        System.out.println(temp.getClass());


        list.get(1);
        for (int i = 0; i < list.size(); i++) {
            
        }

        for (Integer integer : list) {
            
        }

        Iterator<Integer> integerIterator =  list.iterator();
        while (integerIterator.hasNext()){
            System.out.println(integerIterator.next());
        }

        Integer[] tt = new Integer[]{1,1,1,1,1,1,1};

        Integer[] integers =  list.toArray(tt);


    }
}

