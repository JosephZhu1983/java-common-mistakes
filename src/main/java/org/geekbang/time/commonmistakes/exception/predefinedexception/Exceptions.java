package org.geekbang.time.commonmistakes.exception.predefinedexception;

public class Exceptions {

    public static BusinessException ORDEREXISTS = new BusinessException("订单已经存在", 3001);

    public static BusinessException orderExists() {
        return new BusinessException("订单已经存在", 3001);
    }
}
