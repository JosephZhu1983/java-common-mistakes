package org.geekbang.time.commonmistakes.collection.sublist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubListApplication {

    private static List<List<Integer>> data = new ArrayList<>();

    private static void wrong() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.subList(1, 4);
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        try {
            subList.forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        oom();
        //wrong();
        //right1();
//        right2();
        //oomfix();
    }

    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    private static void oomfix() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(new ArrayList<>(rawList.subList(0, 1)));
        }
    }

    private static void right1() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = new ArrayList<>(list.subList(1, 4));
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }

    private static void right2() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.stream().skip(1).limit(3).collect(Collectors.toList());
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }
}

