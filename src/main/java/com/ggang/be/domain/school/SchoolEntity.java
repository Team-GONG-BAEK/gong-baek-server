package com.ggang.be.domain.school;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity(name = "school")
public class SchoolEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Integer id;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String schoolDomain;

    @OneToMany
    private List<SchoolMajorEntity> schoolMajors;
}
