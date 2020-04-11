package org.geekbang.time.commonmistakes.serialization.deserializationconstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deserializationconstructor")
@Slf4j
public class DeserializationConstructorController {
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("wrong")
    public void wrong() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultWrong.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultWrong.class));
    }

    @GetMapping("right")
    public void right() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultRight.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultRight.class));
    }
}
