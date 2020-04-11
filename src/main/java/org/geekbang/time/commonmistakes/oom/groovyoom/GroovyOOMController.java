package org.geekbang.time.commonmistakes.oom.groovyoom;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

@RestController
@RequestMapping("groovyoom")
@Slf4j
public class GroovyOOMController {


    public static final String SCRIPT_TEMPLATE = "a=%s + %s; return a;";
    public static final ConcurrentHashMap<String, Script> SCRIPT_CACHE = new ConcurrentHashMap<>();
    public static final String SCRIPT_METHOD = "add";
    public static final String SCRIPT_PERFECT = "def add(a,b){def c=a+b; return c;}";
    GroovyShell shell = new GroovyShell();

    @GetMapping("wrong")
    public Object wrong() {
        LongStream.rangeClosed(1, 10000).forEach(i -> {
            wrongGroovy(String.format(SCRIPT_TEMPLATE, i, i));
        });
        return wrongGroovy(String.format(SCRIPT_TEMPLATE, 1, 1));
    }

    @GetMapping("right1")
    public Object right1() {
        LongStream.rangeClosed(1, 10000).forEach(i -> {
            wrongGroovy(String.format(SCRIPT_TEMPLATE, i, i));
            shell.resetLoadedClasses();
        });
        return wrongGroovy(String.format(SCRIPT_TEMPLATE, 1, 1));
    }

    @GetMapping("right")
    public Object right() {
        LongStream.rangeClosed(1, 100000).forEach(i -> {
            rightGroovy(SCRIPT_PERFECT, SCRIPT_METHOD, i, i);
        });
        return rightGroovy(SCRIPT_PERFECT, SCRIPT_METHOD, 1, 1);
    }

    private Object wrongGroovy(String script) {
        return shell.evaluate(script);
    }

    private Object rightGroovy(String script, String method, Object... args) {
        Script scriptObject;
        if (SCRIPT_CACHE.containsKey(script)) {
            scriptObject = SCRIPT_CACHE.get(script);
        } else {
            scriptObject = shell.parse(script);
            SCRIPT_CACHE.put(script, scriptObject);
        }
        return scriptObject.invokeMethod(method, args);
    }
}
