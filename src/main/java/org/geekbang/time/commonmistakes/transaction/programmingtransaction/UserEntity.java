package org.geekbang.time.commonmistakes.transaction.programmingtransaction;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String remark;

    public UserEntity() {
    }

    public UserEntity(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }
}
