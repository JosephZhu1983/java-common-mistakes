package org.geekbang.time.commonmistakes.java8;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;


    public static List<Product> getData() {
        return Arrays.asList(
                new Product(1L, "苹果", 1.0),
                new Product(2L, "桔子", 2.0),
                new Product(3L, "香蕉", 3.0),
                new Product(4L, "芒果", 4.0),
                new Product(5L, "西瓜", 5.0),
                new Product(6L, "葡萄", 6.0),
                new Product(7L, "桃子", 7.0),
                new Product(8L, "椰子", 8.0),
                new Product(9L, "菠萝", 9.0),
                new Product(10L, "石榴", 10.0));
    }
}
