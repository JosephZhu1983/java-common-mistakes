package org.geekbang.time.commonmistakes.transaction.nested;

import lombok.Data;

@Data
public class UserData {
    private Long id;
    private String name;
    private String source;
}
