package org.geekbang.time.commonmistakes.advancedfeatures.annotationinheritance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

@Slf4j
public class AnnotationInheritanceApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        wrong();
        right();
    }

    private static String getAnnotationValue(MyAnnotation annotation) {
        if (annotation == null) return "";
        return annotation.value();
    }

    public static void wrong() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));


        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(child.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(child.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));

    }

    public static void right() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo").getAnnotation(MyAnnotation.class)));

        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass(), MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("foo"), MyAnnotation.class)));

    }

    @MyAnnotation(value = "Class")
    @Slf4j
    static class Parent {

        @MyAnnotation(value = "Method")
        public void foo() {
        }
    }

    @Slf4j
    static class Child extends Parent {
        @Override
        public void foo() {
        }
    }
}

