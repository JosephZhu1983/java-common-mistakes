package org.geekbang.time.commonmistakes.transaction.nested;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private SubUserService subUserService;


    @Transactional
    public void createUser(String name) {
        createMainUser(name);
        try {
            subUserService.createSubUser(name);
        } catch (Exception ex) {
            log.error("create sub user error:{}", ex.getMessage());
        }
        //如果createSubUser是NESTED模式，这里抛出异常会导致嵌套事务无法『提交』
        throw new RuntimeException("create main user error");
    }

    private void createMainUser(String name) {
        userDataMapper.insert(name, "main");
    }


    public int getUserCount(String name) {
        return userDataMapper.count(name);
    }
}
