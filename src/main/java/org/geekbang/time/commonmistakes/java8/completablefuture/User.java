package org.geekbang.time.commonmistakes.java8.completablefuture;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private Boolean vip;
}
