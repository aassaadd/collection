package main.java.mo.multithread.volatiletest;

/**
 * 缓存行的原因
 * 差点思考成傻逼了
 */

public class TestTwo {

     int i = 0;

    public void add(){
        System.out.println(i);
        i ++;
    }

    public void testA(){
        while (true){
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

        while (testTwo.i < 1000000){
            testTwo.add();
        }
    }




}
