package main.java.mo.basic;

//import java.util.HashMap;

import java.util.Set;

/**
 * Created by MoXingwang on 2017/6/30.
 */
public class Basic {
    public static void main(String[] args) {
        /*HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("a","11");
        hashMap.get("a");
        hashMap.put("b","11");
        hashMap.put("c","11");
        hashMap.put("d","11");*/

        HashMap<Object,Object> hashMap = new HashMap<Object, Object>();
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.get(new Object());

        Set<Object> r = hashMap.keySet();
        System.out.println("");

    }
}

