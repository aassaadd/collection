package main.java.mo.basic;


import java.io.UnsupportedEncodingException;

/**
 * Created by MoXingwang on 2017/8/26.
 */
public class Test {

    public static void main(String[] args) {
       String s = new String("中中");
        try {
            byte[] a = s.getBytes("utf-8");
            byte[] b = s.getBytes("unicode");

            byte[] c =new byte[5];

            byte[] bytes = {-28,-72,-83,65};
//            byte[] bytes = {-28};
//            byte[] bytes = {65,65,65};


            System.out.println(new String(bytes));

            for (byte so : bytes) {
                System.out.println((char) so);
            }

            System.out.println(a);
            System.out.println(b);

            System.out.println(new String(a));
            System.out.println(new String(b,"unicode"));
            System.out.println(new String(c));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
