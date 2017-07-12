package main.java.mo.sy;

import java.util.List;

/**
 * Created by M on 17/7/12.
 */
public class ObjectA {
    public synchronized void add(List<Integer> list){
        for (int i=0;i<10;i++){
            try {
                System.out.println("ObjectA 执行 i = " + (200 +i));
                list.add(200+i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
