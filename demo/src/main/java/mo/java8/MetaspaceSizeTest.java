package main.java.mo.java8;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MoXingwang on 2017/11/13.
 *
 * 栈内存设置： -Xss112M
 * -Xss1M -Xms1m -Xmx10m -XX:MetaspaceSize=1m -XX:MaxMetaspaceSize=6m
 */
public class MetaspaceSizeTest {
    int i = 0;
    List<java.lang.String> s = new ArrayList<>();

    public static void main(java.lang.String[] args) {
        new MetaspaceSizeTest().test();
    }




    void test(){
        while (true){
            s.add(new java.lang.String());
        }
        /*
        i ++;
        if(i > 1000000){
            return;
        }
        test();*/

    }
}