package com.ggang.be.domain.schoolMajor.infra;

import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolMajorRepository extends JpaRepository<SchoolMajorEntity, Long> {

    @Query("select sm from school_major sm where sm.school.id= :id and sm.majorName like %:schoolMajorKeyword%")
    List<SchoolMajorEntity> findBySchoolIdAndMajorKeyword(Long id, String schoolMajorKeyword);
}
