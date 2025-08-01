package com.ggang.be.api.school;

import com.ggang.be.api.facade.SearchSchoolFacade;
import com.ggang.be.api.school.dto.SchoolSearchResponse;
import com.ggang.be.api.school.service.SchoolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class SearchSchoolFacadeTest {

    private final SchoolService schoolService = Mockito.mock(SchoolService.class);
    private final SearchSchoolFacade searchSchoolFacade = new SearchSchoolFacade(schoolService);

    @DisplayName("검색 키워드로 학교를 검색했을 때 결과를 반환한다.")
    @Test
    void searchSchool() {
        // Given
        String keyword = "서울";
        List<String> mockResult = Arrays.asList("서울대학교", "서울시립대학교");
        when(schoolService.searchSchoolContainingKeywordBoth(keyword)).thenReturn(mockResult);

        // When
        SchoolSearchResponse response = searchSchoolFacade.searchSchool(keyword);

        // Then
        assertThat(response.schoolNames()).containsExactly("서울대학교", "서울시립대학교");
    }

    @DisplayName("검색 키워드가 없는 경우 빈 리스트를 반환한다.")
    @Test
    void searchSchoolNoResult() {
        // Given
        String keyword = "없는학교";
        when(schoolService.searchSchoolContainingKeywordBoth(keyword)).thenReturn(Collections.emptyList());

        // When
        SchoolSearchResponse response = searchSchoolFacade.searchSchool(keyword);

        // Then
        assertThat(response.schoolNames()).isEmpty();
    }

    @DisplayName("검색 키워드가 null인 경우 빈 리스트를 반환한다.")
    @Test
    void searchSchoolNullKeyword() {
        // Given
        when(schoolService.searchSchoolContainingKeywordBoth(null)).thenReturn(Collections.emptyList());

        // When
        SchoolSearchResponse response = searchSchoolFacade.searchSchool(null);

        // Then
        assertThat(response.schoolNames()).isEmpty();
    }
}
