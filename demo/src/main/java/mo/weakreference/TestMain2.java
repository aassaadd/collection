package main.java.mo.weakreference;

import java.lang.ref.WeakReference;

/**
 * Created by MoXingwang on 2017-07-05.
 */
public class TestMain2 {
    public static void main(String[] args) {
        TestObject testObject = new TestObject(100L,"tom");

        WeakReference<TestObject> weakCar = new WeakReference<TestObject>(testObject);

        int i=0;

        while(true){
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - "+weakCar);
            }else{
                System.out.println("Object has been collected.");
                break;
            }
        }

    }
}
