package com.ggang.be.domain.schoolMajor;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.school.SchoolEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "school_major")
public class SchoolMajorEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_major_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @Column(name = "school_major_name", nullable = false)
    private String majorName;

}
