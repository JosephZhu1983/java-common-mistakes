package javaprogramming.commonmistakes.troubleshootingtools.jdktool;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CommonMistakesApplication {

    public static void main(String[] args) throws InterruptedException {
        //wrong: java -jar java-common-mistakes-0.0.1-SNAPSHOT.jar -Xms1g -Xmx1g
        //right: java -Xms1g -Xmx1g -jar java-common-mistakes-0.0.1-SNAPSHOT.jar

        //wrong: java -XX:NativeMemoryTracking=detail -XX:ThreadStackSize=256k -jar java-common-mistakes-0.0.1-SNAPSHOT.jar
        //right: java -XX:NativeMemoryTracking=detail -XX:ThreadStackSize=256 -jar java-common-mistakes-0.0.1-SNAPSHOT.jar

        //-Xms1g -Xmx1g -XX:NativeMemoryTracking=summary
        System.out.println("VM options");
        System.out.println(ManagementFactory.getRuntimeMXBean().getInputArguments().stream().collect(Collectors.joining(System.lineSeparator())));
        System.out.println("Program arguments");
        System.out.println(Arrays.stream(args).collect(Collectors.joining(System.lineSeparator())));

        IntStream.rangeClosed(1, 10).mapToObj(i -> new Thread(() -> {
            while (true) {
                String payload = IntStream.rangeClosed(1, 10000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(payload.length());
            }
        })).forEach(Thread::start);

        TimeUnit.HOURS.sleep(1);
    }
}

