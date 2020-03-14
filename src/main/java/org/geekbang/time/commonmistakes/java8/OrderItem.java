package org.geekbang.time.commonmistakes.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
}
