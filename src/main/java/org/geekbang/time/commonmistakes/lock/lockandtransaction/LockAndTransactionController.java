package org.geekbang.time.commonmistakes.lock.lockandtransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("lockandtransaction")
@Slf4j
public class LockAndTransactionController {

    ExecutorService executors = Executors.newCachedThreadPool();
    @Autowired
    private UserService userService;

    @GetMapping("test3")
    public int test3(@RequestParam("name") String name) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                userService.updateUserCounterWrong(name, 1);
            });

        }
        Thread.sleep(2000);
        return userService.getUserCounter(name);
    }
}
