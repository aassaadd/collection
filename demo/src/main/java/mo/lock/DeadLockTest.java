package main.java.mo.lock;

/**
 * 死锁代码测试
 * Created by M on 17/7/18.
 */
public class DeadLockTest {
    private Object a = new Object();
    private Object b = new Object();

    public void test() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (b) {
                    }

                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (b) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (a) {
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        DeadLockTest deadLockTest = new DeadLockTest();
        deadLockTest.test();
    }

}
