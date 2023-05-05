package org.geekbang.time.commonmistakes.advancedfeatures.reflectioninternal;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class ReflectionTest {
    private static int count = 0;

    public static void test() {
        new Exception("test#" + (count++)).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Method method = ReflectionTest.class.getMethod("test");
        for (int i = 0; i < 20; i++) {
            method.invoke(null);
        }
        TimeUnit.DAYS.sleep(1);
    }
}
