package com.ggang.be.domain.gongbaekTimeTable;

import com.ggang.be.domain.BaseTimeEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import org.springframework.data.annotation.Id;

@Document(collection = "gongbaekTimeTable")
@Getter
@Setter
public class GongbaekTimeTableEntity extends BaseTimeEntity {
    @Id
    private String id;

    private String title;

    private String body;

    private Integer userId;

}
