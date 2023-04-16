package org.geekbang.time.commonmistakes.logging.mdc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("mdc")
@RestController
public class LoggingController {

    @GetMapping("log")
    public void log() {
        log.info("info");
    }

}
