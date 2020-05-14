package org.geekbang.time.commonmistakes.sensitivedata.storepassword;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("storepassword")
public class StorePasswordController {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    @GetMapping("wrong1")
    public UserData wrong1(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setPassword(DigestUtils.md5Hex(password));
        return userRepository.save(userData);
    }

    @GetMapping("wrong2")
    public UserData wrong2(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setPassword(DigestUtils.md5Hex("salt" + password));
        return userRepository.save(userData);
    }

    @GetMapping("wrong3")
    public UserData wrong3(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setPassword(DigestUtils.md5Hex(name + password));
        return userRepository.save(userData);
    }

    @GetMapping("wrong4")
    public UserData wrong4(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(password)));
        return userRepository.save(userData);
    }

    @GetMapping("right")
    public UserData right(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setSalt(UUID.randomUUID().toString());
        userData.setPassword(DigestUtils.md5Hex(userData.getSalt() + password));
        return userRepository.save(userData);
    }


    @GetMapping("better")
    public UserData better(@RequestParam(value = "name", defaultValue = "朱晔") String name, @RequestParam(value = "password", defaultValue = "Abcd1234") String password) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setName(name);
        userData.setPassword(passwordEncoder.encode(password));
        userRepository.save(userData);
        log.info("match ? {}", passwordEncoder.matches(password, userData.getPassword()));
        return userData;
    }

    @GetMapping("performance")
    public void performance() {
        StopWatch stopWatch = new StopWatch();
        String password = "Abcd1234";
        stopWatch.start("MD5");
        DigestUtils.md5Hex(password);
        stopWatch.stop();
        stopWatch.start("BCrypt(10)");
        String hash1 = BCrypt.gensalt(10);
        BCrypt.hashpw(password, hash1);
        System.out.println(hash1);
        stopWatch.stop();
        stopWatch.start("BCrypt(12)");
        String hash2 = BCrypt.gensalt(12);
        BCrypt.hashpw(password, hash2);
        System.out.println(hash2);
        stopWatch.stop();
        stopWatch.start("BCrypt(14)");
        String hash3 = BCrypt.gensalt(14);
        BCrypt.hashpw(password, hash3);
        System.out.println(hash3);
        stopWatch.stop();
        log.info("{}", stopWatch.prettyPrint());
    }

}
