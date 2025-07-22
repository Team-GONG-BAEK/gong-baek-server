package com.ggang.be.domain.group.everyGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.CommentFixture;
import com.ggang.be.domain.group.GroupCommentVoFixture;
import com.ggang.be.domain.group.GroupCommentVoMaker;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.group.everyGroup.infra.EveryGroupRepository;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EveryGroupServiceImplTest {

    @Mock
    private GroupCommentVoMaker groupCommentVoMaker;

    @Mock
    private EveryGroupRepository everyGroupRepository;

    @Mock
    private BlockServiceImpl blockService;

    @InjectMocks
    private EveryGroupServiceImpl everyGroupServiceImpl;

    @Test
    @DisplayName("댓글 작성")
    void writeCommentInGroup() {

        //given
        EveryGroupEntity build = EveryGroupFixture.getTestEveryGroup();
        CommentEntity test = CommentFixture.getTestComment();
        when(everyGroupRepository.findById(1L)).thenReturn(Optional.of(build));

        //when
        everyGroupServiceImpl.writeCommentInGroup(test, 1L);

        //then
        Assertions.assertThat(build.getComments().size()).isEqualTo(1);
        Assertions.assertThat(build.getComments().get(0).getBody()).isEqualTo("test");
    }

    @Test
    @DisplayName("공개 댓글 조회")
    void readCommentInGroupTrueCase() {
        // given
        EveryGroupEntity build = EveryGroupFixture.getTestEveryGroup();
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글 1
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글 2

        List<CommentEntity> commentEntities = build.getComments().stream()
                .filter(CommentEntity::isPublic).toList(); // 필터링된 공개 댓글
        UserEntity userEntity = UserEntityFixture.create();

        when(everyGroupRepository.findById(1L)).thenReturn(Optional.of(build));
        when(groupCommentVoMaker.makeByEveryGroup(any(UserEntity.class), any(List.class), any(EveryGroupEntity.class)))
                .thenReturn(List.of(
                        GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now(), 1L),
                        GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now(), 1L)
                ));

        // when
        ReadCommentGroup readCommentGroup = everyGroupServiceImpl.readCommentInGroup(userEntity,
                true, 1L);

        // then
        Assertions.assertThat(commentEntities).hasSize(2); // 중간 결과 검증
        Assertions.assertThat(readCommentGroup.comments()).hasSize(2); // 2개의 공개 댓글
    }

    @Test
    @DisplayName("비공개 댓글 조회")
    void readCommentInGroupFalseCase() {

        // given
        EveryGroupEntity build = EveryGroupFixture.getTestEveryGroup();
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글 1
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글 2

        List<CommentEntity> commentEntities = build.getComments().stream()
                .filter(c -> !c.isPublic()).toList(); // 필터링된 비공개 댓글

        UserEntity userEntity = UserEntityFixture.create();

        when(everyGroupRepository.findById(1L)).thenReturn(Optional.of(build));
        when(groupCommentVoMaker.makeByEveryGroup(any(UserEntity.class), any(List.class), any(EveryGroupEntity.class)))
                .thenReturn(List.of(
                        GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now(), 1L),
                        GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now(), 1L)
                ));

        // when
        ReadCommentGroup readCommentGroup = everyGroupServiceImpl.readCommentInGroup(userEntity,
                false, 1L);

        // then
        Assertions.assertThat(commentEntities).hasSize(2); // 필터링된 비공개 댓글 확인
        Assertions.assertThat(readCommentGroup.comments()).hasSize(2); // 2개의 비공개 댓글

    }

    @Test
    @DisplayName("댓글 조회시 그룹 발견하지 못한 경우")
    void readCommentInGroupGroupNotFound() {
        // given
        when(everyGroupRepository.findById(99L)).thenReturn(Optional.empty());
        UserEntity userEntity = UserEntityFixture.create();


        // when & then
        Assertions.assertThatThrownBy(() -> everyGroupServiceImpl.readCommentInGroup(userEntity,
                        true, 99L))
                .isInstanceOf(GongBaekException.class)
                .hasMessage(ResponseError.GROUP_NOT_FOUND.getMessage());
    }
}