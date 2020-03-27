package org.geekbang.time.commonmistakes.exception.finallyissue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("finallyissue")
public class FinallyIssueController {

    @GetMapping("useresourcewrong")
    public void useresourcewrong() throws Exception {
        TestResource testResource = new TestResource();
        try {
            testResource.read();
        } finally {
            testResource.close();
        }
    }

    @GetMapping("useresourceright")
    public void useresourceright() throws Exception {
        try (TestResource testResource = new TestResource()) {
            testResource.read();
        }
    }

    @GetMapping("wrong")
    public void wrong() {
        try {
            log.info("try");
            throw new RuntimeException("try");
        } finally {
            log.info("finally");
            throw new RuntimeException("finally");
        }
    }

    @GetMapping("right")
    public void right() {
        try {
            log.info("try");
            throw new RuntimeException("try");
        } finally {
            log.info("finally");
            try {
                throw new RuntimeException("finally");
            } catch (Exception ex) {
                log.error("finally", ex);
            }
        }
    }

    @GetMapping("right2")
    public void right2() throws Exception {
        Exception e = null;
        try {
            log.info("try");
            throw new RuntimeException("try");
        } catch (Exception ex) {
            e = ex;
        } finally {
            log.info("finally");
            try {
                throw new RuntimeException("finally");
            } catch (Exception ex) {
                if (e != null) {
                    e.addSuppressed(ex);
                } else {
                    e = ex;
                }
            }
        }
        throw e;
    }
}
