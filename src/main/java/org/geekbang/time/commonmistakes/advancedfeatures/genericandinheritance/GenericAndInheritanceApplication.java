package org.geekbang.time.commonmistakes.advancedfeatures.genericandinheritance;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class GenericAndInheritanceApplication {

    public static void main(String[] args) {
        wrong3();
    }

    public static void wrong1() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    public static void wrong2() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    public static void wrong3() {
        Child2 child2 = new Child2();
        Arrays.stream(child2.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(method -> {
                    try {
                        method.invoke(child2, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child2.toString());
    }

    public static void right() {
        Child2 child2 = new Child2();
        Arrays.stream(child2.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue") && !method.isBridge())
                .findFirst().ifPresent(method -> {
            try {
                method.invoke(child2, "test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(child2.toString());
    }
}

class Parent<T> {

    AtomicInteger updateCount = new AtomicInteger();

    private T value;

    @Override
    public String toString() {
        return String.format("value: %s updateCount: %d", value, updateCount.get());
    }

    public void setValue(T value) {
        System.out.println("Parent.setValue called");
        this.value = value;
        updateCount.incrementAndGet();
    }
}

class Child1 extends Parent {


    public void setValue(String value) {
        System.out.println("Child1.setValue called");
        super.setValue(value);
    }
}


class Child2 extends Parent<String> {

    @Override
    public void setValue(String value) {
        System.out.println("Child2.setValue called");
        super.setValue(value);
    }
}