package org.geekbang.time.commonmistakes.sensitivedata.storeidcard;

import lombok.Data;

@Data
public class CipherResult {
    private Long id;
    private String cipherText;
}
