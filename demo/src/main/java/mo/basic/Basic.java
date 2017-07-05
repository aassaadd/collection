package main.java.mo.basic;

//import java.util.HashMap;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

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

        WeakHashMap<String,String> weakHashMap = new WeakHashMap<>();
        weakHashMap.put("1","1");
        weakHashMap.put("2","2");

        HashMap<Object,Object> hashMap = new HashMap<Object, Object>();
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.put(new Object(),"11");
        hashMap.get(new Object());
        Hashtable<String,String> hashtable = new Hashtable<>();
        hashtable.put("a","11");
        hashtable.get("a");
        hashtable.put("b","11");
        hashtable.put("c","11");
        hashtable.size();

        hashtable.put("c",null);

        Set<Object> r = hashMap.keySet();
        System.out.println("");

    }
}

