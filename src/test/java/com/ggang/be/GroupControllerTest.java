package com.ggang.be;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.GroupFacade;
import com.ggang.be.api.group.GroupController;
import com.ggang.be.domain.constant.WeekDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@DisplayName("🔍 GroupController 단위 테스트 (Spring Boot 3.4+)")
public class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupFacade groupFacade;

    @InjectMocks
    private GroupController groupController;

    public GroupControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    @DisplayName("✅ 모임 정보 조회 - 성공 (WEEKLY)")
    void getGroupInfoWeeklySuccess() throws Exception {
        // Given
        GroupResponse mockResponse = new GroupResponse(
                1L,
                "WEEKLY",
                "스터디 모임",
                "도서관",
                true,
                5,
                10,
                "스터디 모임입니다.",
                "STUDY",
                WeekDate.WED,
                null,
                10.5,
                12.0
        );

        when(groupFacade.getGroupInfo("WEEKLY", 1L)).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/fill/info")
                        .header("Authorization", "Bearer mockToken")
                        .param("groupType", "WEEKLY")
                        .param("groupId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(ResponseSuccess.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseSuccess.OK.getMessage()))
                .andExpect(jsonPath("$.data.groupType").value("WEEKLY"))
                .andExpect(jsonPath("$.data.groupTitle").value("스터디 모임"));
    }

    @Test
    @DisplayName("❌ 잘못된 groupId (0 이하)")
    void getGroupInfoInvalidGroupId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/fill/info")
                        .header("Authorization", "Bearer mockToken")
                        .param("groupType", "WEEKLY")
                        .param("groupId", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("❌ 잘못된 groupType")
    void getGroupInfoInvalidGroupType() throws Exception {
        when(groupFacade.getGroupInfo(anyString(), anyLong()))
                .thenThrow(new IllegalArgumentException("Invalid groupType: INVALID"));

        // When & Then
        mockMvc.perform(get("/api/v1/fill/info")
                        .header("Authorization", "Bearer mockToken")
                        .param("groupType", "INVALID")
                        .param("groupId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid groupType: INVALID"));
    }

    @Test
    @DisplayName("❌ Authorization 헤더 없음")
    void getGroupInfoNoAuthorizationHeader() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/fill/info")
                        .param("groupType", "WEEKLY")
                        .param("groupId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
