package main.java.mo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by M on 17/7/12.
 */
public class TestA implements Runnable{
    Lock lock = new ReentrantLock();

    @Override
    public void run() {

    }
}
