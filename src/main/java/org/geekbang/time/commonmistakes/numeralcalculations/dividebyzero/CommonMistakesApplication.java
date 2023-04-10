package org.geekbang.time.commonmistakes.numeralcalculations.dividebyzero;


import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertThrows;


@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) {

        assertThrows(ArithmeticException.class, () -> {
            int result = 12 / 0;
        });

        float result1 = 12f / 0;
        System.out.println("result1:" + result1);
        System.out.println("result1==Float.POSITIVE_INFINITY:" + (result1 == Float.POSITIVE_INFINITY));
        float result2 = -12f / 0;
        System.out.println("result2:" + result2);
        System.out.println("result2==Float.NEGATIVE_INFINITY:" + (result2 == Float.NEGATIVE_INFINITY));
        float result3 = 0.0f / 0;
        System.out.println("result3:" + result3);
        System.out.println("result3==Float.NaN:" + (result3 == Float.NaN));
        System.out.println("Float.compare(result3, Float.NaN)==0:" + (Float.compare(result3, Float.NaN) == 0));
        System.out.println("Float.isNaN(result3)):" + Float.isNaN(result3));
    }

}

