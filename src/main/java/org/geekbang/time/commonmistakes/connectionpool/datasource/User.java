package org.geekbang.time.commonmistakes.connectionpool.datasource;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
}
