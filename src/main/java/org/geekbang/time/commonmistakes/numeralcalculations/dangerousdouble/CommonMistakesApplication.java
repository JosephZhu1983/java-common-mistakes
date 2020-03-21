package org.geekbang.time.commonmistakes.numeralcalculations.dangerousdouble;


import java.math.BigDecimal;

public class CommonMistakesApplication {

    public static void main(String[] args) {

        test();
        System.out.println("wrong1");
        wrong1();
        System.out.println("wrong2");
        wrong2();
        System.out.println("right");
        right();
    }

    private static void wrong1() {

        System.out.println(0.1 + 0.2);
        System.out.println(1.0 - 0.8);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);

        double amount1 = 2.15;
        double amount2 = 1.10;

        if (amount1 - amount2 == 1.05)
            System.out.println("OK");
    }

    private static void test() {
        System.out.println(Long.toBinaryString(Double.doubleToRawLongBits(0.1)));
        System.out.println(Long.toBinaryString(Double.doubleToRawLongBits(0.2)));

    }

    private static void wrong2() {
        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
        System.out.println(new BigDecimal(1.0).subtract(new BigDecimal(0.8)));
        System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal(123.3).divide(new BigDecimal(100)));
    }

    private static void right() {
        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
        System.out.println(new BigDecimal("1.0").subtract(new BigDecimal("0.8")));
        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal("100")));
        System.out.println(new BigDecimal("123.3").divide(new BigDecimal("100")));

        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal(Double.toString(100))));
        System.out.println(new BigDecimal("4.015").multiply(BigDecimal.valueOf(100)));

    }
}

