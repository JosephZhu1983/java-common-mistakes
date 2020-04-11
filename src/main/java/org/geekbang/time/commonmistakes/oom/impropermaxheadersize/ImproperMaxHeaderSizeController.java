package org.geekbang.time.commonmistakes.oom.impropermaxheadersize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("impropermaxheadersize")
@Slf4j
public class ImproperMaxHeaderSizeController {
    @Autowired
    private Environment env;

    //wrk -t10 -c100 -d 60s http://localhost:45678/impropermaxheadersize/oom
    //-Xmx1g -Xms1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.
    //https://tomcat.apache.org/tomcat-8.0-doc/config/http.html
    @GetMapping("oom")
    public String oom() {
        log.info("Got request");
        log.info("server.max-http-header-size={},server.tomcat.max-threads={}",
                env.getProperty("server.max-http-header-size")
                , env.getProperty("server.tomcat.max-threads")
        );
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }

}
