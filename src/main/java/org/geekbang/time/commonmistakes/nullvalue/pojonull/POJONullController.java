package org.geekbang.time.commonmistakes.nullvalue.pojonull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("pojonull")
@RestController
public class POJONullController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("wrong")
    public User wrong(@RequestBody User user) {
        user.setNickname(String.format("guest%s", user.getName()));
        return userRepository.save(user);
    }

    @PostMapping("right")
    public UserEntity right(@RequestBody UserDto user) {
        if (user == null || user.getId() == null)
            throw new IllegalArgumentException("用户Id不能为空");

        UserEntity userEntity = userEntityRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        if (user.getName() != null) {
            userEntity.setName(user.getName().orElse(""));
        }
        userEntity.setNickname("guest" + userEntity.getName());
        if (user.getAge() != null) {
            userEntity.setAge(user.getAge().orElseThrow(() -> new IllegalArgumentException("年龄不能为空")));
        }
        return userEntityRepository.save(userEntity);
    }
}