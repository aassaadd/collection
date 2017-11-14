package main.java.mo.java8;

/**
 * @author MoXingwang on 2017/11/13.
 *
 * 栈内存设置： -Xss112M
 *
 */
public class MetaspaceSizeTest {
    int i = 0;
    public static void main(String[] args) {
        new MetaspaceSizeTest().test();
    }

    void test(){
        i ++;
        if(i > 1000000){
            return;
        }
        test();
    }
}
