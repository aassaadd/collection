package main.java.mo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by M on 17/7/12.
 */
public class ReentrantLockTest {
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        test.test();
    }

    int size = 0;


    public void test() {
        Thread thread1 = new Thread(() -> {
            testA();
        });

        Thread thread2 = new Thread(() -> {
            testA();
        });

        thread1.setName("thread1");
        thread1.start();;
        thread2.setName("thread2");
        thread2.start();

    }

    public void testA() {

        lock.lock();
        lock.lock();
        for (int i = 0; i < 1000000; i++) {
            size = size + 1;
            System.out.println(Thread.currentThread().getName() + " : " + size);
        }
        lock.unlock();
    }

    public void testB() {
        for (int i = 0; i < 1000; i++) {
            size++;
            System.out.println(size);
        }
    }

}
