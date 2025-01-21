package com.ggang.be.api.school;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.SearchSchoolMajorFacade;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.schoolMajor.dto.SearchedSchoolMajorResponse;
import com.ggang.be.api.schoolMajor.service.SchoolMajorService;
import com.ggang.be.domain.school.application.School;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

class SearchSchoolMajorFacadeTest {

    private final SchoolMajorService schoolMajorService = Mockito.mock(SchoolMajorService.class);
    private final SchoolService schoolService = Mockito.mock(SchoolService.class);
    private final SearchSchoolMajorFacade searchSchoolMajorFacade = new SearchSchoolMajorFacade(schoolMajorService, schoolService);

    @DisplayName("학교 이름과 학과 키워드로 학과를 검색했을 때 결과를 반환한다.")
    @Test
    void searchSchoolMajorBySchoolName() {
        // Given
        String schoolName = "서울대학교";
        String majorKeyword = "컴퓨터";
        School mockSchool = School.builder().id(1L).schoolName("서울대학교").schoolDomain("서울대학교").build();
        List<String> mockMajors = Arrays.asList("컴퓨터공학과", "컴퓨터과학과");

        when(schoolService.findSchoolByName(schoolName)).thenReturn(mockSchool);
        when(schoolMajorService.findSchoolMajorBySchoolAndMajorName(mockSchool.getId(), majorKeyword)).thenReturn(mockMajors);

        // When
        SearchedSchoolMajorResponse response = searchSchoolMajorFacade.searchSchoolMajorBySchoolName(schoolName, majorKeyword);

        // Then
        assertThat(response.schoolMajors()).containsExactly("컴퓨터공학과", "컴퓨터과학과");
    }


    @DisplayName("학교 이름이 잘못되었을 경우 예외가 발생하고 'NOT_FOUND' 응답을 반환한다.")
    @Test
    void searchSchoolMajorByInvalidSchoolName() {
        // Given
        String invalidSchoolName = "없는학교";
        String majorKeyword = "컴퓨터";

        when(schoolService.findSchoolByName(invalidSchoolName)).thenThrow(new GongBaekException(ResponseError.NOT_FOUND));

        // When & Then
        assertThatThrownBy(() -> searchSchoolMajorFacade.searchSchoolMajorBySchoolName(invalidSchoolName, majorKeyword)).isInstanceOf(GongBaekException.class).hasMessage(ResponseError.NOT_FOUND.getMessage());
    }

    @DisplayName("학교 이름이 null 일 경우 예외가 발생하고 'NOT_FOUND' 응답을 반환한다.")
    @Test
    void searchSchoolMajorBySchoolNameNull() {
        // Given
        String majorKeyword = "컴퓨터";

        when(schoolService.findSchoolByName(null)).thenThrow(new GongBaekException(ResponseError.NOT_FOUND));

        // When & Then
        assertThatThrownBy(() -> searchSchoolMajorFacade.searchSchoolMajorBySchoolName(null, majorKeyword)).isInstanceOf(GongBaekException.class).hasMessage(ResponseError.NOT_FOUND.getMessage());
    }

    @DisplayName("학과 키워드가 없는 경우 빈 결과를 반환한다.")
    @Test
    void searchSchoolMajorWithoutKeyword() {
        // Given
        String schoolName = "서울대학교";
        String emptyKeyword = "";
        School mockSchool = School.builder().id(1L).schoolName("서울대학교").schoolDomain("서울대학교").build();

        when(schoolService.findSchoolByName(schoolName)).thenReturn(mockSchool);
        when(schoolMajorService.findSchoolMajorBySchoolAndMajorName(mockSchool.getId(), emptyKeyword)).thenReturn(Collections.emptyList());

        // When
        SearchedSchoolMajorResponse response = searchSchoolMajorFacade.searchSchoolMajorBySchoolName(schoolName, emptyKeyword);

        // Then
        assertThat(response.schoolMajors()).isEmpty();
    }
}

