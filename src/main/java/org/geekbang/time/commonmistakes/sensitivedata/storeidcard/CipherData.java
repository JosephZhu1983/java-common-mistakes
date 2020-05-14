package org.geekbang.time.commonmistakes.sensitivedata.storeidcard;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class CipherData {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String iv;//初始化向量
    private String secureKey;//密钥
}
