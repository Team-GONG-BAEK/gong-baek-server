package com.ggang.be.domain.school.infra;

import com.ggang.be.domain.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    @Query("select s from school s where s.schoolName like %:searchKeyword%")
    List<SchoolEntity> findContainingSearchKeyword(String searchKeyword);

    Optional<SchoolEntity> findBySchoolName(String schoolName);
}
