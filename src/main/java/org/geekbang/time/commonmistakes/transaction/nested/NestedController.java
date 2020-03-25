package org.geekbang.time.commonmistakes.transaction.nested;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("nested")
public class NestedController {
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public int right() {
        String name = UUID.randomUUID().toString();
        log.info("create user {}", name);
        try {
            userService.createUser(name);
        } catch (Exception ex) {
            log.error("create user error:{}", ex.getMessage());
        }
        return userService.getUserCount(name);
    }
}
