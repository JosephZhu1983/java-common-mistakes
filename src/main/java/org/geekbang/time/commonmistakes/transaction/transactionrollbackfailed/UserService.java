package org.geekbang.time.commonmistakes.transaction.transactionrollbackfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUserWrong1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
        }
    }

    @Transactional
    public void createUserWrong2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }


    @Transactional
    public void createUserRight1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ", userRepository.findByName(name).size());//为什么这里是1你能想明白吗？
    }

    //DefaultTransactionAttribute
    @Transactional(rollbackFor = Exception.class)
    public void createUserRight2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

}
