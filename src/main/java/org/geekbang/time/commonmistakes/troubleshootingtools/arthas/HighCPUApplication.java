package org.geekbang.time.commonmistakes.troubleshootingtools.arthas;

import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HighCPUApplication {

    private static byte[] payload = IntStream.rangeClosed(1, 10_000)
            .mapToObj(__ -> "a")
            .collect(Collectors.joining("")).getBytes();
    private static Random random = new Random();

    public static void main(String[] args) {
        task();
    }

    private static void task() {
        while (true) {
            doTask(random.nextInt(100));
        }
    }

    private static void doTask(int i) {
        if (i == User.ADMIN_ID) {
            IntStream.rangeClosed(1, 10000).parallel().forEach(j -> DigestUtils.md5DigestAsHex(payload));
        }
    }
}
