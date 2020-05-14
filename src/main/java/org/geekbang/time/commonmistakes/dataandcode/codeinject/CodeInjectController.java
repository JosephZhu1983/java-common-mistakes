package org.geekbang.time.commonmistakes.dataandcode.codeinject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("codeinject")
@Slf4j
@RestController
public class CodeInjectController {
    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine jsEngine = scriptEngineManager.getEngineByName("js");

    //haha';java.lang.System.exit(0);'
    @GetMapping("wrong")
    public Object wrong(@RequestParam("name") String name) {
        try {
            return jsEngine.eval(String.format("var name='%s'; name=='admin'?1:0;", name));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("right")
    public Object right(@RequestParam("name") String name) {
        try {
            Map<String, Object> parm = new HashMap<>();
            parm.put("name", name);
            return jsEngine.eval("name=='admin'?1:0;", new SimpleBindings(parm));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("right2")
    public Object right2(@RequestParam("name") String name) throws InstantiationException {
        ScriptingSandbox scriptingSandbox = new ScriptingSandbox(jsEngine);
        return scriptingSandbox.eval(String.format("var name='%s'; name=='admin'?1:0;", name));
    }
}
