package org.geekbang.time.commonmistakes.lock.lockandtransaction;

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

    private int counter;

    public UserEntity() {
    }

    public UserEntity(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }
}
