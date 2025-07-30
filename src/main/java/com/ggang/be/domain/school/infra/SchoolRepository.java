package com.ggang.be.domain.school.infra;

import com.ggang.be.domain.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    @Query("select s from school s where LOWER(s.schoolName) like LOWER(CONCAT('%', :searchKeyword, '%'))")
    List<SchoolEntity> findContainingSearchKeyword(String searchKeyword);

    @Query("select s from school s where LOWER(s.schoolNameEn) like LOWER(CONCAT('%', :searchKeyword, '%'))")
    List<SchoolEntity> findContainingSearchKeywordEn(String searchKeyword);

    @Query("select s from school s where LOWER(s.schoolName) like LOWER(CONCAT('%', :searchKeyword, '%')) or LOWER(s.schoolNameEn) like LOWER(CONCAT('%', :searchKeyword, '%'))")
    List<SchoolEntity> findContainingSearchKeywordBoth(String searchKeyword);

    Optional<SchoolEntity> findBySchoolName(String schoolName);

    @Query("SELECT s.schoolDomain FROM school s WHERE s.schoolName = :schoolName")
    Optional<String> findDomainBySchoolName(String schoolName);
}
