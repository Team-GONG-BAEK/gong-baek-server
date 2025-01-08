package com.ggang.be.domain.school.application;

import static org.mockito.Mockito.when;

import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.dto.SchoolSearchVo;
import com.ggang.be.domain.school.infra.SchoolRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchoolServiceImplTest {


    @InjectMocks
    private SchoolServiceImpl schoolServiceImpl;

    @Mock
    private SchoolRepository schoolRepository;

    @Test
    @DisplayName("학교 이름으로 해당 학교 이름 가지고 있는 학교 검색")
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

        when(schoolRepository.findContainingSearchKeyword("schoolName")).thenReturn(List.of(schoolDomain1, schoolDomain2));

        // when
        List<SchoolSearchVo> schoolName = schoolServiceImpl.searchSchoolContainingKeyword(
            "schoolName");

        // then
        Assertions.assertThat(schoolName).hasSize(2)
            .extracting("schoolName")
            .containsExactly("1", "2");
    }


}