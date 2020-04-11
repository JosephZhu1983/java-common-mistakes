package org.geekbang.time.commonmistakes.datetime.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "datetest")
public class TimeEntity {
    @Id
    private Long id;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime mydatetime;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime mytimestamp;

}
