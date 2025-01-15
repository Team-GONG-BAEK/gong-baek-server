package com.ggang.be.domain.schoolMajor;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.school.SchoolEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "school_major")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolMajorEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_major_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @Column(name = "school_major_name", nullable = false)
    private String majorName;

    @Builder
    private SchoolMajorEntity(SchoolEntity school, String majorName) {
        this.school = school;
        this.majorName = majorName;
    }
}
