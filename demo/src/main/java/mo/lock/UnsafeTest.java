package main.java.mo.lock;

import sun.misc.Unsafe;

/**
 * Created by MoXingwang on 2017-07-12.
 */
public class UnsafeTest {
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    public static void main(String[] args) {

        UnsafeTest test = new UnsafeTest();
        test.test();
    }

    public  void test() {

        int a = 1;
        unsafe.compareAndSwapInt(this, 1L, a, 2);

        System.out.println(a);

    }
}
