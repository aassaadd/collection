package main.java.mo.multithread.volatiletest;

/**
 * 缓存行的原因
 * 差点思考成傻逼了
 */

public class TestTwo {

     int i = 0;
    volatile int b = 0;

    public void add(){
        System.out.println(i);
        i ++;
        b ++;
    }

    public void testA(){
        while (true){
//            Integer.toString(i);

//            toString(i);

//            String s = new String("11212");
//            Object a = new Integer(10);

//            String aaa = "dsfsdfsd";

//                new Object();

//            if(b > 0){
                System.out.println("b " + b);
//            }

            if(i > 0){
                System.out.println("------------------i > 0");
                break;
            }
        }
    }

    public static void main(String[] args) {
        TestTwo testTwo = new TestTwo();
        Thread thread1 = new Thread(() -> {
            testTwo.testA();
        });
        thread1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (testTwo.i < 10000){
            testTwo.add();
        }
    }




}
