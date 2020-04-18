package org.geekbang.time.commonmistakes.springpart1.aopmetrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Metrics(ignoreException = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
