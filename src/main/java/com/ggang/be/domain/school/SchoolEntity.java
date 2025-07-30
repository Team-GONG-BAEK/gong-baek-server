package com.ggang.be.domain.school;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "school_name_idx", columnList = "schoolName"),
        @Index(name = "school_name_en_idx", columnList = "schoolNameEn")
})
@Getter
public class SchoolEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(nullable = false)
    // TODO : unique 제약 조건 달기!
    private String schoolName;

    @Column(name = "school_name_en")
    private String schoolNameEn;

    @Column(nullable = false)
    private String schoolDomain;

    @OneToMany(mappedBy = "school")
    private List<SchoolMajorEntity> schoolMajors;

    @OneToMany(mappedBy = "school")
    private List<UserEntity> user;

    @Builder
    private SchoolEntity(
            String schoolName,
            String schoolNameEn,
            String schoolDomain,
            List<SchoolMajorEntity> schoolMajors
    ) {
        this.schoolName = schoolName;
        this.schoolNameEn = schoolNameEn;
        this.schoolDomain = schoolDomain;
        this.schoolMajors = schoolMajors;
    }
}
