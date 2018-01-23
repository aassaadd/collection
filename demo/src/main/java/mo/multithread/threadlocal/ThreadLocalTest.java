package main.java.mo.multithread.threadlocal;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MoXingwang on 2018/1/23.
 * http://blog.csdn.net/ni357103403/article/details/51970748
 */
public class ThreadLocalTest implements Runnable {


    private static ThreadLocal<String> sdfMap = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return Thread.currentThread().getName();
        }
    };

    @Override
    public void run() {
        String a = sdfMap.get();
        System.out.println(a);
    }

    public static void main(String[] args) {
        new Thread(new ThreadLocalTest()).start();
        new Thread(new ThreadLocalTest()).start();
        new Thread(new ThreadLocalTest()).start();
        System.out.println("");
    }

}
