package main.java.mo.sy;

/**
 * Created by M on 17/7/16.
 */
public class ObjectC {
    int a = 0;

    public synchronized void testa(){
        for (int i =0;i<10000;i++){
            System.out.println("testa:" + ++a);
        }
    }

    public void testb(){
        for (int i =0;i<10000;i++){
            System.out.println("testb:"+ ++a);
        }
    }

    public static void main(String[] args) {
        ObjectC objectC = new ObjectC();

        Thread threada = new Thread(new Runnable() {
            @Override
            public void run() {
                objectC.testa();
            }
        });

        Thread threadb = new Thread(new Runnable() {
            @Override
            public void run() {
                objectC.testb();
            }
        });

        threada.start();
        threadb.start();

    }
}
