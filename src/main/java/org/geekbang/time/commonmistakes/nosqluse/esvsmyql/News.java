package org.geekbang.time.commonmistakes.nosqluse.esvsmyql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

@Entity
@Document(indexName = "news", replicas = 0) //ES
@Table(name = "news", indexes = {@Index(columnList = "cateid")}) //MySQL
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class News {
    @Id
    private long id;

    @Field(type = FieldType.Keyword)
    private String category;

    private int cateid;

    @Column(columnDefinition = "varchar(500)")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Column(columnDefinition = "text")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;
}
