package com.ggang.be.api.block;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.facade.CommentFacade;
import com.ggang.be.api.comment.registry.CommentStrategy;
import com.ggang.be.api.comment.registry.CommentStrategyRegistry;
import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockingCommentTest {

    @Mock
    private CommentStrategyRegistry commentStrategyRegistry;

    @Mock
    private UserService userService;

    @Mock
    private BlockServiceImpl blockService;

    @Mock
    private ReportService reportService;

    @Mock
    private CommentStrategy commentStrategy;

    @InjectMocks
    private CommentFacade commentFacade;

    private UserEntity testUser;
    private ReadCommentRequest readCommentRequest;
    private List<Long> blockedUserIds;
    private List<GroupCommentVo> commentVos;
    private ReadCommentResponse originalResponse;
    private List<ReportEntity> testReports;

    @BeforeEach
    void setUp() {
        testUser = UserEntityFixture.createByNickname("testUser");
        ReflectionTestUtils.setField(testUser, "id", 1L);
        readCommentRequest = new ReadCommentRequest(1L, GroupType.ONCE);
        blockedUserIds = List.of(4L, 5L);
        testReports = List.of();

        commentVos = createTestCommentData();

        ReadCommentGroup commentGroup = new ReadCommentGroup(
                commentVos.size(),
                1L,
                GroupType.ONCE,
                Status.RECRUITING,
                commentVos
        );
        originalResponse = ReadCommentResponse.of(commentGroup);

        setupCommonMocks();
    }

    @Test
    @DisplayName("차단된 사용자의 댓글이 필터링되어야 한다")
    void shouldFilterBlockedUserComments() {
        // 차단 목록 설정
        when(reportService.findReportedUserIds(testUser.getId()))
                .thenReturn(blockedUserIds);

        // When
        ReadCommentResponse result = commentFacade.readComment(
                testUser.getId(), true, readCommentRequest);

        // Then
        List<GroupCommentVo> filteredComments = result.readCommentGroup().comments();
        assertThat(filteredComments).hasSize(3);
        assertThat(filteredComments)
                .extracting(GroupCommentVo::userId)
                .containsOnly(1L, 2L, 3L)
                .doesNotContain(4L, 5L);
    }

    @Test
    @DisplayName("차단 목록이 비어있으면 모든 댓글이 반환되어야 한다")
    void shouldReturnAllCommentsWhenNoBlockedUsers() {
        // 차단 목록을 빈 리스트로 설정
        when(reportService.findReportedUserIds(testUser.getId()))
                .thenReturn(List.of());

        // When
        ReadCommentResponse result = commentFacade.readComment(
                testUser.getId(), true, readCommentRequest);

        // Then
        List<GroupCommentVo> allComments = result.readCommentGroup().comments();
        assertThat(allComments).hasSize(5);
        assertThat(allComments)
                .extracting(GroupCommentVo::nickname)
                .containsExactly("testUser", "normalUser1", "normalUser2", "blockedUser1", "blockedUser2");
    }

    private void setupCommonMocks() {
        when(commentStrategyRegistry.getCommentGroupStrategy(any(GroupType.class)))
                .thenReturn(commentStrategy);
        when(userService.getUserById(testUser.getId()))
                .thenReturn(testUser);
        when(commentStrategy.readComment(testUser, true, readCommentRequest))
                .thenReturn(originalResponse);
    }

    private List<GroupCommentVo> createTestCommentData() {
        LocalDateTime now = LocalDateTime.now();

        return List.of(
                createCommentVo(1L, "testUser", now),
                createCommentVo(2L, "normalUser1", now),
                createCommentVo(3L, "normalUser2", now),
                createCommentVo(4L, "blockedUser1", now),
                createCommentVo(5L, "blockedUser2", now)
        );
    }

    private GroupCommentVo createCommentVo(Long commentId, String nickname, LocalDateTime createdAt) {
        Long dummyUserId = switch (nickname) {
            case "testUser" -> 1L;
            case "normalUser1" -> 2L;
            case "normalUser2" -> 3L;
            case "blockedUser1" -> 4L;
            case "blockedUser2" -> 5L;
            default -> 999L;
        };

        return new GroupCommentVo(
                commentId,
                false,
                false,
                nickname,
                "Test comment body",
                createdAt.toString(),
                dummyUserId
        );
    }
}
