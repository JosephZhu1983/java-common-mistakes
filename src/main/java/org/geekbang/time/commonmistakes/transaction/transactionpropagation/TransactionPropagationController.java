package org.geekbang.time.commonmistakes.transaction.transactionpropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactionpropagation")
@Slf4j
public class TransactionPropagationController {

    @Autowired
    private UserService userService;

    @GetMapping("wrong")
    public int wrong(@RequestParam("name") String name) {
        try {
            userService.createUserWrong(new UserEntity(name));
        } catch (Exception ex) {
            log.error("createUserWrong failed, reason:{}", ex.getMessage());
        }
        return userService.getUserCount(name);
    }

    @GetMapping("wrong2")
    public int wrong2(@RequestParam("name") String name) {
        try {
            userService.createUserWrong2(new UserEntity(name));
        } catch (Exception ex) {
            log.error("createUserWrong2 failed, reason:{}", ex.getMessage(), ex);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right")
    public int right(@RequestParam("name") String name) {
        userService.createUserRight(new UserEntity(name));
        return userService.getUserCount(name);
    }
}
