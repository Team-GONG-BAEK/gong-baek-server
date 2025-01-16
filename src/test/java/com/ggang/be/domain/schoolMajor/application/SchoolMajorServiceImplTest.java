package com.ggang.be.domain.schoolMajor.application;

import static org.mockito.Mockito.when;

import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import com.ggang.be.domain.schoolMajor.dto.SearchSchoolMajorVo;
import com.ggang.be.domain.schoolMajor.infra.SchoolMajorRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchoolMajorServiceImplTest {


    @Mock
    private SchoolMajorRepository schoolMajorRepository;


    @InjectMocks
    private SchoolMajorServiceImpl schoolMajorServiceImpl;


    @Test
    @DisplayName("학교 이름, 학과 이름으로 해당 학과 이름 가지고 있는 학교 검색")
    void searchSchoolContainingKeyword() {
        // given
        SchoolEntity schoolDomain1 = SchoolEntity.builder()
            .schoolDomain("schoolDomain1")
            .schoolName("1")
            .schoolMajors(List.of())
            .build();

        SchoolEntity schoolDomain2 = SchoolEntity.builder()
            .schoolDomain("schoolDomain2")
            .schoolName("2")
            .schoolMajors(List.of())
            .build();

        SchoolMajorEntity major = SchoolMajorEntity.builder()
            .school(schoolDomain1)
            .majorName("12")
            .build();

        SchoolMajorEntity major2 = SchoolMajorEntity.builder()
            .school(schoolDomain2)
            .majorName("123")
            .build();


        when(schoolMajorRepository.findBySchoolIdAndMajorKeyword(1L, "test"))
            .thenReturn(List.of(major, major2));

        // when
        List<String> findTestSchoolMajor = schoolMajorServiceImpl.findSchoolMajorBySchoolAndMajorName(
            1L, "test");

        // then
        Assertions.assertThat(findTestSchoolMajor).hasSize(2)
            .containsExactly("12", "123");
    }



}