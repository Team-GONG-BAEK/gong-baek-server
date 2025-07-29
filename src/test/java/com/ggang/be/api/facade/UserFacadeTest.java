package com.ggang.be.api.facade;

import com.ggang.be.api.user.facade.UserFacade;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.block.BlockEntity;
import com.ggang.be.domain.block.infra.BlockRepository;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.infra.CommentRepository;
import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.report.infra.ReportRepository;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private CommentRepository commentRepository;

    private UserEntity user1;
    private UserEntity user2;
    private UserEntity user3;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 초기화
        userRepository.deleteAll();
        reportRepository.deleteAll();
        blockRepository.deleteAll();
        commentRepository.deleteAll();

        // 테스트용 사용자 생성
        user1 = createUser("user1", "user1@test.com");
        user2 = createUser("user2", "user2@test.com");
        user3 = createUser("user3", "user3@test.com");
    }

    @Test
    @DisplayName("회원탈퇴 시 신고한 댓글과 관련 블록이 모두 삭제된다")
    @Transactional
    void deleteUserWithReportedComments() {
        // given
        Long userId = user1.getId();
        Long reportedUserId = user2.getId();

        // user1이 user2의 댓글을 신고 (자동으로 블록 생성됨)
        CommentEntity comment = createComment(user2, "신고당할 댓글");
        ReportEntity report = createReport(comment.getId(), userId, reportedUserId, ReportType.COMMENT);
        BlockEntity block = createBlock(report, user2);

        // 다른 사용자의 신고는 남겨둠
        CommentEntity otherComment = createComment(user3, "다른 댓글");
        ReportEntity otherReport = createReport(otherComment.getId(), user3.getId(), user1.getId(), ReportType.COMMENT);

        // when
        userFacade.deleteUser(userId);

        // then
        // user1이 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();

        // user1이 신고한 신고와 user1을 신고한 신고가 모두 삭제되었는지 확인
        List<ReportEntity> reports = reportRepository.findAll();
        System.out.println("Remaining reports: " + reports.size());
        for (ReportEntity remainingReport : reports) {
            System.out.println("Report - ID: " + remainingReport.getId() + ", ReportUserId: " + remainingReport.getReportUserId() + ", ReportedUserId: " + remainingReport.getReportedUserId());
        }
        assertThat(reports).isEmpty();

        // user1이 신고한 블록이 삭제되었는지 확인
        List<BlockEntity> blocks = blockRepository.findAll();
        assertThat(blocks).isEmpty();
    }

    @Test
    @DisplayName("회원탈퇴 시 신고한 그룹과 관련 블록이 모두 삭제된다")
    @Transactional
    void deleteUserWithReportedGroups() {
        // given
        Long userId = user1.getId();
        Long reportedUserId = user2.getId();
        Long groupId = 1L;

        // user1이 user2의 그룹을 신고 (자동으로 블록 생성됨)
        ReportEntity report = createReport(groupId, userId, reportedUserId, ReportType.ONCE_GROUP);
        BlockEntity block = createBlock(report, user2);

        // 다른 사용자의 신고는 남겨둠
        ReportEntity otherReport = createReport(2L, user3.getId(), user1.getId(), ReportType.WEEKLY_GROUP);

        // when
        userFacade.deleteUser(userId);

        // then
        // user1이 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();

        // user1이 신고한 신고와 user1을 신고한 신고가 모두 삭제되었는지 확인
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).isEmpty();

        // user1이 신고한 블록이 삭제되었는지 확인
        List<BlockEntity> blocks = blockRepository.findAll();
        assertThat(blocks).isEmpty();
    }

    @Test
    @DisplayName("회원탈퇴 시 신고당한 사용자에 대한 모든 신고가 삭제된다")
    @Transactional
    void deleteUserWithReportsAgainstHim() {
        // given
        Long userId = user1.getId();
        Long reporter1Id = user2.getId();
        Long reporter2Id = user3.getId();

        // user2와 user3가 user1을 신고
        CommentEntity comment = createComment(user1, "신고당할 댓글");
        ReportEntity report1 = createReport(comment.getId(), reporter1Id, userId, ReportType.COMMENT);
        ReportEntity report2 = createReport(comment.getId(), reporter2Id, userId, ReportType.COMMENT);

        // user1이 다른 사용자를 신고한 것은 남겨둠
        CommentEntity otherComment = createComment(user2, "다른 댓글");
        ReportEntity otherReport = createReport(otherComment.getId(), userId, user2.getId(), ReportType.COMMENT);

        // when
        userFacade.deleteUser(userId);

        // then
        // user1이 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();

        // user1이 신고한 신고와 user1을 신고한 신고가 모두 삭제되었는지 확인
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).isEmpty();
    }

    @Test
    @DisplayName("회원탈퇴 시 작성한 댓글의 작성자가 제거된다")
    @Transactional
    void deleteUserWithComments() {
        // given
        Long userId = user1.getId();

        // user1이 작성한 댓글들
        CommentEntity comment1 = createComment(user1, "첫 번째 댓글");
        CommentEntity comment2 = createComment(user1, "두 번째 댓글");

        // 다른 사용자의 댓글은 남겨둠
        CommentEntity otherComment = createComment(user2, "다른 사용자 댓글");

        // when
        userFacade.deleteUser(userId);

        // then
        // user1이 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();

        // user1이 작성한 댓글들의 작성자가 제거되었는지 확인
        List<CommentEntity> comments = commentRepository.findAll();
        assertThat(comments).hasSize(3);

        CommentEntity updatedComment1 = commentRepository.findById(comment1.getId()).orElseThrow();
        CommentEntity updatedComment2 = commentRepository.findById(comment2.getId()).orElseThrow();
        CommentEntity updatedOtherComment = commentRepository.findById(otherComment.getId()).orElseThrow();

        assertThat(updatedComment1.getUserEntity()).isNull();
        assertThat(updatedComment2.getUserEntity()).isNull();
        assertThat(updatedOtherComment.getUserEntity()).isNotNull();
    }

    @Test
    @DisplayName("회원탈퇴 시 복합적인 신고 상황에서도 정상적으로 처리된다")
    @Transactional
    void deleteUserWithComplexReportScenario() {
        // given
        Long userId = user1.getId();
        Long reportedUserId = user2.getId();

        // user1이 user2를 신고한 상황들
        CommentEntity comment = createComment(user2, "신고당할 댓글");
        ReportEntity commentReport = createReport(comment.getId(), userId, reportedUserId, ReportType.COMMENT);
        BlockEntity commentBlock = createBlock(commentReport, user2);

        // user1이 user3를 신고한 상황
        ReportEntity groupReport = createReport(1L, userId, user3.getId(), ReportType.ONCE_GROUP);
        BlockEntity groupBlock = createBlock(groupReport, user3);

        // user2가 user1을 신고한 상황
        CommentEntity user1Comment = createComment(user1, "user1의 댓글");
        ReportEntity user1Report = createReport(user1Comment.getId(), reportedUserId, userId, ReportType.COMMENT);

        // when
        userFacade.deleteUser(userId);

        // then
        // user1이 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();

        // user1이 신고한 모든 신고와 user1을 신고한 모든 신고가 삭제되었는지 확인
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).isEmpty();

        // user1이 신고한 모든 블록이 삭제되었는지 확인
        List<BlockEntity> blocks = blockRepository.findAll();
        assertThat(blocks).isEmpty();
    }

    @Test
    @DisplayName("회원탈퇴 시 데이터 무결성 제약에 걸리지 않는다")
    @Transactional
    void deleteUserWithoutIntegrityConstraintViolation() {
        // given
        Long userId = user1.getId();
        Long reportedUserId = user2.getId();

        // 복잡한 연관관계 생성
        CommentEntity comment = createComment(user2, "신고당할 댓글");
        ReportEntity report = createReport(comment.getId(), userId, reportedUserId, ReportType.COMMENT);
        BlockEntity block = createBlock(report, user2);

        // when & then
        // 예외가 발생하지 않고 정상적으로 삭제되어야 함
        userFacade.deleteUser(userId);

        // 모든 관련 데이터가 정상적으로 삭제되었는지 확인
        assertThat(userRepository.findById(userId)).isEmpty();
        assertThat(reportRepository.findAll()).isEmpty();
        assertThat(blockRepository.findAll()).isEmpty();
    }

    private UserEntity createUser(String nickname, String email) {
        UserEntity user = UserEntity.builder()
                .nickname(nickname)
                .email(email)
                .platform(com.ggang.be.domain.constant.Platform.KAKAO)
                .platformId(nickname + "123")
                .schoolMajorName("컴퓨터공학과")
                .profileImg(1)
                .enterYear(2024)
                .mbti(com.ggang.be.domain.constant.Mbti.ENFP)
                .gender(com.ggang.be.domain.constant.Gender.MAN)
                .introduction("테스트 사용자")
                .build();
        return userRepository.save(user);
    }

    private CommentEntity createComment(UserEntity user, String body) {
        CommentEntity comment = CommentEntity.builder()
                .userEntity(user)
                .body(body)
                .isPublic(true)
                .build();
        return commentRepository.save(comment);
    }

    private ReportEntity createReport(Long targetId, Long reportUserId, Long reportedUserId, ReportType targetType) {
        ReportEntity report = ReportEntity.builder()
                .targetId(targetId)
                .targetType(targetType)
                .reportUserId(reportUserId)
                .reportedUserId(reportedUserId)
                .build();
        return reportRepository.save(report);
    }

    private BlockEntity createBlock(ReportEntity report, UserEntity user) {
        BlockEntity block = BlockEntity.builder()
                .report(report)
                .user(user)
                .build();
        return blockRepository.save(block);
    }
} 