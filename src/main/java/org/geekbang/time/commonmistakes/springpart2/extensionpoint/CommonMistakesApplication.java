package org.geekbang.time.commonmistakes.springpart2.extensionpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class CommonMistakesApplication implements CommandLineRunner {
    @Resource
    private MyService helloService;
    @Autowired
    private MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Bean(initMethod = "init", name = "helloService")
    public MyService helloService() {
        return new MyService();
    }

    @Override
    public void run(String... args) throws Exception {
        myService.hello();
        helloService.hello();
    }
}

