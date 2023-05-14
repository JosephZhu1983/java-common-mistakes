package org.geekbang.time.commonmistakes.lock.lockandtransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private Lock lock = new ReentrantLock();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUserCounterWrong(String name, int i) {
        // 执行任意数据库查询操作。此时事务已开启
        List<UserEntity> userList = userRepository.findByName(name);
        if (userList.size() > 0) {
            log.info("updateUserCounterWrong {} +{}", name, i);
            // 为了提高性能，某些情况不需要加锁进行更新。另一些情况需要加锁后更新
            if (true) {
                lock.lock();
                // 加锁后重新查询数据库，期望获得最新数据
                // 这里错误。因为事务在加锁前已经开启，所以实际无法获得最新 counter
                userList = userRepository.findByName(name);
                UserEntity user = userList.get(0);
                try {
                    // 模拟耗时操作
                    Thread.sleep(1000);
                    // 这里错误。因为counter不是最新的，会导致counter计算错误
                    user.setCounter(user.getCounter() + i);
                    userRepository.save(user);
                } catch (InterruptedException ignore) {
                } finally {
                    lock.unlock();
                }
            }

        } else {
            userRepository.save(new UserEntity(name, ""));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int getUserCounter(String name) {
        List<UserEntity> userList = userRepository.findByName(name);
        if (userList.size() > 0) {
            UserEntity user = userList.get(0);
            return user.getCounter();
        }
        return -1;
    }
}
