package org.geekbang.time.commonmistakes.transaction.programmingtransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }

    @Transactional
    public void createUser1(String name) {
        userRepository.save(new UserEntity(name, "[1]"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ignore) {
        }
        userRepository.save(new UserEntity(name, "[2]"));
        if (name.equals("error"))
            throw new RuntimeException("error");
    }

    @Resource
    private TransactionTemplate transactionTemplate;

    public void createUser2(String name) {
        List<Runnable> list = new ArrayList<>();
        list.add(() -> userRepository.save(new UserEntity(name, "[1]")));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ignore) {
        }
        list.add(() -> {
            userRepository.save(new UserEntity(name, "[2]"));
            if (name.equals("error"))
                throw new RuntimeException("error");
        });
        transactionTemplate.setName("createUser2");
        transactionTemplate.executeWithoutResult(s -> list.forEach(Runnable::run));
    }
}
