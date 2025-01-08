package com.ggang.be.domain.school.infra;

import com.ggang.be.domain.school.SchoolEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    @Query("select s from school s where s.schoolName like %:schoolName%")
    List<SchoolEntity> findContainingSchoolName(String schoolName);

}
