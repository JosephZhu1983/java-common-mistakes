package org.geekbang.time.commonmistakes.transaction.programmingtransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("programmingtransaction")
@Slf4j
public class ProgrammingTransactionController {

    @Autowired
    private UserService userService;

    @GetMapping("test1")
    public int test1(@RequestParam("name") String name) {
        userService.createUser1(name);
        return userService.getUserCount(name);
    }

    @GetMapping("test2")
    public int test2(@RequestParam("name") String name) {
        userService.createUser2(name);
        return userService.getUserCount(name);
    }
}
