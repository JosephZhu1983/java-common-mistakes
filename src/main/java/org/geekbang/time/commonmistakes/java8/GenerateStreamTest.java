package org.geekbang.time.commonmistakes.java8;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenerateStreamTest {

    @Test
    public void of() {
        String[] arr = {"a", "b", "c"};
        Stream.of(arr).forEach(System.out::println);
        Stream.of("a", "b", "c").forEach(System.out::println);
        Stream.of(1, 2, "a").map(item -> item.getClass().getName()).forEach(System.out::println);
    }

    @Test
    public void iterate() {
        Stream.iterate(2, item -> item * 2).limit(10).forEach(System.out::println);
        Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN)).limit(10).forEach(System.out::println);
    }

    @Test
    public void generate() {
        Stream.generate(() -> "test").limit(3).forEach(System.out::println);
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    @Test
    public void stream() {
        Arrays.asList("a1", "a2", "a3").stream().forEach(System.out::println);
        Arrays.stream(new int[]{1, 2, 3}).forEach(System.out::println);
    }

    @Test
    public void primitive() {
        //演示IntStream和DoubleStream
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.range(0, 3).mapToObj(i -> "x").forEach(System.out::println);

        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        DoubleStream.of(1.1, 2.2, 3.3).forEach(System.out::println);


        //各种转换
        System.out.println(IntStream.of(1, 2).toArray().getClass()); //class [I
        System.out.println(Stream.of(1, 2).mapToInt(Integer::intValue).toArray().getClass()); //class [I
        System.out.println(IntStream.of(1, 2).boxed().toArray().getClass()); //class [Ljava.lang.Object;
        System.out.println(IntStream.of(1, 2).asDoubleStream().toArray().getClass()); //class [D
        System.out.println(IntStream.of(1, 2).asLongStream().toArray().getClass()); //class [J


        Arrays.asList("a", "b", "c").stream()   // Stream<String>
                .mapToInt(String::length)       // IntStream
                .asLongStream()                 // LongStream
                .mapToDouble(x -> x / 10.0)     // DoubleStream
                .boxed()                        // Stream<Double>
                .mapToLong(x -> 1L)             // LongStream
                .mapToObj(x -> "")              // Stream<String>
                .collect(Collectors.toList());
    }

}
