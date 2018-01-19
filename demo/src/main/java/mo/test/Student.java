package main.java.mo.test;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MoXingwang on 2018-01-18.
 */
@Data
@Getter
@Setter
public class Student {

    private String name;

    public static void main(String[] args) {
        Student student = new Student();


    }

}
