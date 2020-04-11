package org.geekbang.time.commonmistakes.serialization.enumusedinapi;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
enum StatusEnumServer {
    CREATED(1, "已创建"),
    PAID(2, "已支付"),
    DELIVERED(3, "已送到"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消");
    @JsonValue
    private final int status;
    private final String desc;

    StatusEnumServer(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

//    @JsonCreator
//    public static StatusEnumServer parse(Object o) {
//        return Arrays.stream(StatusEnumServer.values()).filter(value->o.equals(value.status)).findFirst().orElse(null);
//    }
}
