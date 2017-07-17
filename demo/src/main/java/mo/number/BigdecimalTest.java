package main.java.mo.number;

import java.math.BigDecimal;

/**
 * Created by M on 17/7/17.
 */
public class BigdecimalTest {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(1.2282);

        System.out.println(bigDecimal.setScale(2,BigDecimal.ROUND_DOWN));
    }
}
