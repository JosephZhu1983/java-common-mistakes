package org.geekbang.time.commonmistakes.advancedfeatures.reflectionissue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionIssueApplication {

    public static void main(String[] args) throws Exception {

        ReflectionIssueApplication application = new ReflectionIssueApplication();
        application.wrong();
        application.right();

    }

    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("Integer age = {}", age);
    }

    public void wrong() throws Exception {
        getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, Integer.valueOf("36"));
    }

    public void right() throws Exception {
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, Integer.valueOf("36"));
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, 36);
    }
}
