package org.geekbang.time.commonmistakes.datetime.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Entity
@Data
@Table(name = "datetest")
public class TimeEntity {
    @Id
    private Long id;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime mydatetime1;
    @Column(columnDefinition = "TIMESTAMP NULL")
    private LocalDateTime mytimestamp1;
    @Column(columnDefinition = "DATETIME")
    private ZonedDateTime mydatetime2;
    @Column(columnDefinition = "TIMESTAMP NULL")
    private ZonedDateTime mytimestamp2;
    @Column(columnDefinition = "DATETIME")
    private String mydatetime3;
    @Column(columnDefinition = "TIMESTAMP NULL")
    private String mytimestamp3;

}
