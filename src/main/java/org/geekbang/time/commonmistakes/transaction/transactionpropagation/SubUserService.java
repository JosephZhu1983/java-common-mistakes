package org.geekbang.time.commonmistakes.transaction.transactionpropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class SubUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createSubUserWithExceptionWrong(UserEntity entity) {
        log.info("createSubUserWithExceptionWrong start");
        userRepository.save(entity);
        throw new RuntimeException("invalid status");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSubUserWithExceptionRight(UserEntity entity) {
        log.info("createSubUserWithExceptionRight start");
        userRepository.save(entity);
        throw new RuntimeException("invalid status");
    }
}
