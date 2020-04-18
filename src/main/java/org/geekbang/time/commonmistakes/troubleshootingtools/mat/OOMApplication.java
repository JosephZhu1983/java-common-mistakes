package org.geekbang.time.commonmistakes.troubleshootingtools.mat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OOMApplication implements CommandLineRunner {

    @Autowired
    FooService fooService;

    //-Xmx512m -Xms512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.
    public static void main(String[] args) {
        SpringApplication.run(OOMApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            fooService.oom();
        }
    }
}
