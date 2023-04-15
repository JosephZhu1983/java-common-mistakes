package org.geekbang.time.commonmistakes.oom.oomtype;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CommonMistakesApplication {

    static javassist.ClassPool cp = javassist.ClassPool.getDefault();

    public static void main(String[] args) throws Exception {
        test2();
    }

    private static void test1() {
        int[] i = new int[Integer.MAX_VALUE];
        //Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit
    }

    private static void test2() throws Exception {
        //-XX:CompressedClassSpaceSize=10M
        for (int i = 0; ; i++) {
            Class c = cp.makeClass("outofmemory.test" + i).toClass();
        }
        //Caused by: java.lang.ClassFormatError: Compressed class space
    }

    private static void test3() throws Exception {
        //-XX:MaxMetaspaceSize=20M
        for (int i = 0; ; i++) {
            Class c = cp.makeClass("outofmemory.test" + i).toClass();
        }
        //Caused by: java.lang.ClassFormatError: Metaspace
    }

    private static void test4() {
        //-Xmx20m
        //-Xms20m
        //-XX:-OmitStackTraceInFastThrow
        int _2MB = 1024 * 1024 * 2;

        Map<Integer, Integer> m = new HashMap<>();
        Random r = new Random();
        for (int i = 1; ; i++) {
            m.put(r.nextInt(), i);
        }

        //Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
    }

    private static void test5() {
        for (int i = 0; ; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        //Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
    }
}

