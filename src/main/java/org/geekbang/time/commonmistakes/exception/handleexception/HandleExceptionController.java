package org.geekbang.time.commonmistakes.exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("handleexception")
public class HandleExceptionController {
    @GetMapping("exception")
    public void exception(@RequestParam("business") boolean b) {
        if (b)
            throw new BusinessException("订单不存在", 2001);
        throw new RuntimeException("系统错误");
    }

    @GetMapping("wrong1")
    public void wrong1() {
        try {
            readFile();
        } catch (IOException e) {
            throw new RuntimeException("系统忙请稍后再试");
        }
    }

    @GetMapping("wrong2")
    public void wrong2() {
        try {
            readFile();
        } catch (IOException e) {
            log.error("文件读取错误, {}", e.getMessage());
            throw new RuntimeException("系统忙请稍后再试");
        }
    }

    @GetMapping("wrong3")
    public void wrong3(@RequestParam("orderId") String orderId) {
        try {
            readFile();
        } catch (Exception e) {
            log.error("文件读取错误", e);
            throw new RuntimeException();
        }
    }

    @GetMapping("right1")
    public void right1() {
        try {
            readFile();
        } catch (IOException e) {
            log.error("文件读取错误", e);
            throw new RuntimeException("系统忙请稍后再试");
        }
    }

    @GetMapping("right2")
    public void right2() {
        try {
            readFile();
        } catch (IOException e) {
            throw new RuntimeException("系统忙请稍后再试", e);
        }
    }


    private void readFile() throws IOException {
        Files.readAllLines(Paths.get("a_file"));
    }
}
