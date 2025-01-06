package com.ggang.be.domain.lectureTimeTable;

import com.ggang.be.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lectureTimeTable")
@Getter
@Setter
public class LectureTimeTableEntity extends BaseTimeEntity {
    @Id
    private String id;

    private String title;

    private String body;

    private Integer userId;

}
