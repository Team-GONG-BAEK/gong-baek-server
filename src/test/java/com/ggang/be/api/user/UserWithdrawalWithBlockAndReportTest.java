package com.ggang.be.api.user;

import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.user.facade.UserFacade;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.report.application.ReportServiceImpl;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserWithdrawalWithBlockAndReportTest {


    @Mock
    private BlockServiceImpl blockService;

    @Mock
    private ReportServiceImpl reportService;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @Mock
    private LectureTimeSlotService lectureTimeSlotService;

    @Mock
    private EveryGroupService everyGroupService;

    @Mock
    private OnceGroupService onceGroupService;

    @Mock
    private GongbaekTimeSlotService gongbaekTimeSlotService;

    @Mock
    private UserOnceGroupService userOnceGroupService;

    @Mock
    private UserEveryGroupService userEveryGroupService;

    @InjectMocks
    private UserFacade userFacade;


    @Test
    @DisplayName("신고당한 유저가 탈퇴하면 Block 데이터만 삭제된다.")
    void deleteReportedUser() {
        // given
        UserEntity reportedUser = UserEntityFixture.createByNickname("reportedUser");
        Long reportedUserId = 2L;

        // userService mock 설정
        when(userService.getUserById(reportedUserId)).thenReturn(reportedUser);

        // when
        userFacade.deleteUser(reportedUserId);

        // then
        verify(blockService).deleteBlocksByUser(reportedUser);
        verify(reportService).deleteAllReportsByUser(reportedUserId);
        verify(userService).deleteUser(reportedUserId);
    }

    @Test
    @DisplayName("신고한 유저가 탈퇴하면 Report와 Block 데이터 모두 삭제된다.")
    void deleteReportingUser() {
        // given
        UserEntity reportingUser = UserEntityFixture.createByNickname("reportingUser");
        Long reportingUserId = 1L;

        // userService mock 설정
        when(userService.getUserById(reportingUserId)).thenReturn(reportingUser);

        // when
        userFacade.deleteUser(reportingUserId);

        // then
        verify(blockService).deleteBlocksByUser(reportingUser);
        verify(reportService).deleteAllReportsByUser(reportingUserId);
        verify(userService).deleteUser(reportingUserId);
    }
}
