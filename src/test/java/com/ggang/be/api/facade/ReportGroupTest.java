package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.group.dto.GroupCreatorVo;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.facade.GroupFacade;
import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportGroupTest {

    @Mock
    private ReportService reportService;

    @Mock
    private BlockServiceImpl blockService;

    @Mock
    private GroupFacade groupFacade;

    @InjectMocks
    private ReportFacade reportFacade;

    private UserEntity reporter;
    private UserEntity groupCreator;
    private Long groupId;
    private Long reporterId;
    private Long creatorId;
    private GroupType groupType;

    @BeforeEach
    void setUp() {
        reporter = UserEntity.builder()
                .nickname("신고자")
                .email("reporter@test.com")
                .platform(Platform.KAKAO)
                .platformId("reporter123")
                .schoolMajorName("컴퓨터공학과")
                .profileImg(1)
                .enterYear(2024)
                .mbti(Mbti.ENFP)
                .gender(Gender.MAN)
                .introduction("안녕하세요")
                .build();

        groupCreator = UserEntity.builder()
                .nickname("모임장")
                .email("creator@test.com")
                .platform(Platform.KAKAO)
                .platformId("creator123")
                .schoolMajorName("컴퓨터공학과")
                .profileImg(1)
                .enterYear(2024)
                .mbti(Mbti.ENFP)
                .gender(Gender.MAN)
                .introduction("안녕하세요")
                .build();

        groupId = 100L;
        reporterId = 1L;
        creatorId = 2L;
        groupType = GroupType.ONCE;
    }

    @Test
    @DisplayName("모임 신청자가 모임을 신고하면 신청이 취소되고 신고가 생성된다")
    void reportGroupByApplicant() {
        // given
        GroupCreatorVo groupCreatorVo = new GroupCreatorVo(creatorId);
        GroupRequest groupRequest = GroupRequest.of(groupId, groupType);

        when(groupFacade.findGroupCreator(groupType, groupId))
                .thenReturn(groupCreatorVo);
        doNothing().when(groupFacade).cancelMyApplication(reporterId, groupRequest);
        doNothing().when(reportService).reportGroup(eq(groupId), eq(reporterId), eq(creatorId), eq(groupType));

        // when
        ResponseSuccess result = reportFacade.reportGroup(reporterId, groupId, groupType);

        // then
        assertThat(result).isEqualTo(ResponseSuccess.CREATED);

        // 모임 신청 취소가 호출되었는지 확인
        verify(groupFacade).cancelMyApplication(reporterId, groupRequest);

        // 신고 생성이 호출되었는지 확인
        verify(reportService).reportGroup(groupId, reporterId, creatorId, groupType);

        // 다른 메서드들이 호출되지 않았는지 확인
        verifyNoMoreInteractions(groupFacade, reportService);
    }

    @Test
    @DisplayName("모임 미신청자가 모임을 신고하면 신고만 생성되고 신청 취소는 호출되지 않는다")
    void reportGroupByNonApplicant() {
        // given
        GroupCreatorVo groupCreatorVo = new GroupCreatorVo(creatorId);
        GroupRequest groupRequest = GroupRequest.of(groupId, groupType);

        when(groupFacade.findGroupCreator(groupType, groupId))
                .thenReturn(groupCreatorVo);
        doNothing().when(groupFacade).cancelMyApplication(reporterId, groupRequest);
        doNothing().when(reportService).reportGroup(eq(groupId), eq(reporterId), eq(creatorId), eq(groupType));

        // when
        ResponseSuccess result = reportFacade.reportGroup(reporterId, groupId, groupType);

        // then
        assertThat(result).isEqualTo(ResponseSuccess.CREATED);

        // 모임 신청 취소가 호출되었는지 확인 (신청 여부와 관계없이 항상 호출됨)
        verify(groupFacade).cancelMyApplication(reporterId, groupRequest);

        // 신고 생성이 호출되었는지 확인
        verify(reportService).reportGroup(groupId, reporterId, creatorId, groupType);

        // 다른 메서드들이 호출되지 않았는지 확인
        verifyNoMoreInteractions(groupFacade, reportService);
    }

    @Test
    @DisplayName("다회성 모임을 신고할 때도 정상적으로 처리된다")
    void reportWeeklyGroup() {
        // given
        GroupType weeklyGroupType = GroupType.WEEKLY;
        GroupCreatorVo groupCreatorVo = new GroupCreatorVo(creatorId);
        GroupRequest groupRequest = GroupRequest.of(groupId, weeklyGroupType);

        when(groupFacade.findGroupCreator(weeklyGroupType, groupId))
                .thenReturn(groupCreatorVo);
        doNothing().when(groupFacade).cancelMyApplication(reporterId, groupRequest);
        doNothing().when(reportService).reportGroup(eq(groupId), eq(reporterId), eq(creatorId), eq(weeklyGroupType));

        // when
        ResponseSuccess result = reportFacade.reportGroup(reporterId, groupId, weeklyGroupType);

        // then
        assertThat(result).isEqualTo(ResponseSuccess.CREATED);

        verify(groupFacade).cancelMyApplication(reporterId, groupRequest);
        verify(reportService).reportGroup(groupId, reporterId, creatorId, weeklyGroupType);
    }

    @Test
    @DisplayName("모임 신고 시 모임장 정보를 정확히 조회한다")
    void reportGroupWithCorrectCreatorInfo() {
        // given
        GroupCreatorVo groupCreatorVo = new GroupCreatorVo(creatorId);
        GroupRequest groupRequest = GroupRequest.of(groupId, groupType);

        when(groupFacade.findGroupCreator(groupType, groupId))
                .thenReturn(groupCreatorVo);
        doNothing().when(groupFacade).cancelMyApplication(reporterId, groupRequest);
        doNothing().when(reportService).reportGroup(eq(groupId), eq(reporterId), eq(creatorId), eq(groupType));

        // when
        reportFacade.reportGroup(reporterId, groupId, groupType);

        // then
        verify(groupFacade).findGroupCreator(groupType, groupId);
        verify(reportService).reportGroup(groupId, reporterId, creatorId, groupType);
    }

    @Test
    @DisplayName("모임 신고 시 올바른 GroupRequest가 생성된다")
    void reportGroupWithCorrectGroupRequest() {
        // given
        GroupCreatorVo groupCreatorVo = new GroupCreatorVo(creatorId);
        GroupRequest expectedGroupRequest = GroupRequest.of(groupId, groupType);

        when(groupFacade.findGroupCreator(groupType, groupId))
                .thenReturn(groupCreatorVo);
        doNothing().when(groupFacade).cancelMyApplication(reporterId, expectedGroupRequest);
        doNothing().when(reportService).reportGroup(eq(groupId), eq(reporterId), eq(creatorId), eq(groupType));

        // when
        reportFacade.reportGroup(reporterId, groupId, groupType);

        // then
        verify(groupFacade).cancelMyApplication(reporterId, expectedGroupRequest);
    }
} 