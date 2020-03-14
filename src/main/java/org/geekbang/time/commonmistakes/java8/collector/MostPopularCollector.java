package org.geekbang.time.commonmistakes.java8.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MostPopularCollector<T> implements Collector<T, Map<T, Integer>, Optional<T>> {
    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, Integer>, T> accumulator() {
        return (acc, elem) -> acc.merge(elem, 1, (old, value) -> old + value);
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return null;
    }

    @Override
    public Function<Map<T, Integer>, Optional<T>> finisher() {
        return (acc) -> acc.entrySet().stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? a : b)
                .map(Map.Entry::getKey);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
