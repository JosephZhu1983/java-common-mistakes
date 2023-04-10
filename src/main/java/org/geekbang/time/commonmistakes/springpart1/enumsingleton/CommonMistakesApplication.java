package org.geekbang.time.commonmistakes.springpart1.enumsingleton;

import java.util.stream.IntStream;

public class CommonMistakesApplication {
    public static void main(String[] args) {
        StatusEnum statusEnum1 = StatusEnum.CREATED;
        statusEnum1.setReason("statusEnum1");
        StatusEnum statusEnum2 = StatusEnum.CREATED;
        System.out.println(statusEnum1 == statusEnum2);
        System.out.println(statusEnum2.reason);

        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> {
            StatusEnum statusEnum = StatusEnum.CREATED;
            statusEnum.setReason(String.valueOf(i));
            log(i, statusEnum);
        });
    }

    static void log(int i, StatusEnum statusEnum) {
        if (!statusEnum.reason.equals(String.valueOf(i)))
            System.out.println("id:" + i + " / " + statusEnum);
    }

    enum StatusEnum {
        CREATED(1000, "已创建");
        private final Integer status;
        private final String desc;
        private String reason;

        StatusEnum(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public StatusEnum setReason(String reason) {
            this.reason = reason;
            return this;
        }

        @Override
        public String toString() {
            return "reason:" + reason + ",desc:" + desc;
        }
    }
}

