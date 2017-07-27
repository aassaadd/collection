package main.java.mo.multithread.volatiletest;

/**
 * Created by M on 17/7/23.
 * java -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:CompileCommand=compileonly,*Bar.sum Bar
 *
 * export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/bin/javac

 */
public class Bar {

    private static int id = 111;

    public static void main(String[] args) {
//        System.out.println(id);
    }

}
