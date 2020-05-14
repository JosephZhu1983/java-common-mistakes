package org.geekbang.time.commonmistakes.securitylastdefense.prventcouponfarming;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CouponCenter {

    AtomicInteger totalSent = new AtomicInteger(0);

    public void sendCoupon(Coupon coupon) {
        if (coupon != null)
            totalSent.incrementAndGet();
    }

    public int getTotalSentCoupon() {
        return totalSent.get();
    }

    public Coupon generateCouponWrong(long userId, BigDecimal amount) {
        return new Coupon(userId, amount);
    }

    public Coupon generateCouponRight(long userId, CouponBatch couponBatch) {
        if (couponBatch.getRemainCount().decrementAndGet() >= 0) {
            return new Coupon(userId, couponBatch.getAmount());
        } else {
            log.info("优惠券批次 {} 剩余优惠券不足", couponBatch.getId());
            return null;
        }
    }

    public CouponBatch generateCouponBatch() {
        CouponBatch couponBatch = new CouponBatch();
        couponBatch.setAmount(new BigDecimal("100"));
        couponBatch.setId(1L);
        couponBatch.setTotalCount(new AtomicInteger(100));
        couponBatch.setRemainCount(couponBatch.getTotalCount());
        couponBatch.setReason("XXX活动");
        return couponBatch;
    }
}
