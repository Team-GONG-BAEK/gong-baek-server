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
    @Index(name="school_name_idx", columnList = "schoolName")
})
@Getter
public class SchoolEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String schoolDomain;

    @OneToMany(mappedBy = "school")
    private List<SchoolMajorEntity> schoolMajors;

    @OneToMany(mappedBy = "school")
    private List<UserEntity> user;

    @Builder
    private SchoolEntity(String schoolName, String schoolDomain,
        List<SchoolMajorEntity> schoolMajors) {
        this.schoolName = schoolName;
        this.schoolDomain = schoolDomain;
        this.schoolMajors = schoolMajors;
    }
}
