package org.geekbang.time.commonmistakes.transaction.transactionrollbackfailed;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactionrollbackfailed")
@Slf4j
public class TransactionRollbackFailedController {

    @Autowired
    private UserService userService;

    @GetMapping("wrong1")
    public int wrong1(@RequestParam("name") String name) {
        userService.createUserWrong1(name);
        return userService.getUserCount(name);
    }

    @GetMapping("wrong2")
    public int wrong2(@RequestParam("name") String name) {
        try {
            userService.createUserWrong2(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right1")
    public int right1(@RequestParam("name") String name) {
        try {
            userService.createUserRight1(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right2")
    public int right2(@RequestParam("name") String name) {
        try {
            userService.createUserRight2(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }
}
