package org.geekbang.time.commonmistakes.serialization.enumusedinapi;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
enum StatusEnumClient {
    CREATED(1, "已创建"),
    PAID(2, "已支付"),
    DELIVERED(3, "已送到"),
    FINISHED(4, "已完成"),
    @JsonEnumDefaultValue
    UNKNOWN(-1, "未知");

    @JsonValue
    private final int status;
    private final String desc;

    StatusEnumClient(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    //    @JsonCreator
//    public static StatusEnumClient parse(Object o) {
//        return Arrays.stream(StatusEnumClient.values()).filter(value->o.equals(value.status)).findFirst().orElse(null);
//    }
}
