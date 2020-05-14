package org.geekbang.time.commonmistakes.securitylastdefense.prventcouponfarming;

import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class CouponBatch {
    private long id;
    private AtomicInteger totalCount;
    private AtomicInteger remainCount;
    private BigDecimal amount;
    private String reason;
}
