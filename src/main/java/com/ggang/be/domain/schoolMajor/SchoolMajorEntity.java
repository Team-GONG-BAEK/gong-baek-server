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
@Table(indexes = {
        @Index(name = "school_major_name_idx", columnList = "school_major_name"),
        @Index(name = "school_major_name_en_idx", columnList = "school_major_name_en")
})
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

    @Column(name = "school_major_name_en")
    private String majorNameEn;

    @Builder
    private SchoolMajorEntity(SchoolEntity school, String majorName, String majorNameEn) {
        this.school = school;
        this.majorName = majorName;
        this.majorNameEn = majorNameEn;
    }
}
