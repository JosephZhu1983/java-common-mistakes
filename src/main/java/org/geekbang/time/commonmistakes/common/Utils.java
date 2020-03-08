package org.geekbang.time.commonmistakes.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class Utils {

    public static void loadPropertySource(Class clazz,String fileName){
        try {
            Properties p=new Properties();
            p.load(clazz.getResourceAsStream(fileName));
            p.forEach((k,v)->{
                log.info("{}={}",k,v);
                System.setProperty(k.toString(),v.toString());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
