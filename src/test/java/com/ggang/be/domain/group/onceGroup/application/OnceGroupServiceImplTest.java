package com.ggang.be.domain.group.onceGroup.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.CommentFixture;
import com.ggang.be.domain.group.GroupCommentVoFixture;
import com.ggang.be.domain.group.GroupCommentVoMaker;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.group.onceGroup.infra.OnceGroupRepository;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OnceGroupServiceImplTest {

    @Mock
    private GroupCommentVoMaker groupCommentVoMaker;

    @Mock
    private OnceGroupRepository onceGroupRepository;

    @InjectMocks
    private OnceGroupServiceImpl onceGroupServiceImpl;

    @Test
    @DisplayName("댓글 작성")
    void writeCommentInGroup() {

        //given
        OnceGroupEntity build = OnceGroupFixture.getTestOnceGroupEntity();
        CommentEntity test = CommentFixture.getTestComment();
        when(onceGroupRepository.findById(1L)).thenReturn(Optional.of(build));

        //when
        onceGroupServiceImpl.writeCommentInGroup(test, 1L);

        //then
        Assertions.assertThat(build.getComments().size()).isEqualTo(1);
        Assertions.assertThat(build.getComments().get(0).getBody()).isEqualTo("test");

    }

    @Test
    @DisplayName("공개 댓글 조회")
    void readCommentInGroupTrueCase() {
        // given
        OnceGroupEntity build = OnceGroupFixture.getTestOnceGroupEntity();
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글 1
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글 2

        List<CommentEntity> commentEntities = build.getComments().stream()
            .filter(CommentEntity::isPublic).toList(); // 필터링된 공개 댓글

        UserEntity testUserEntity = UserEntityFixture.create();

        when(onceGroupRepository.findById(1L)).thenReturn(Optional.of(build));
        when(groupCommentVoMaker.makeByOnceGroup(eq(testUserEntity), eq(commentEntities), eq(build))).thenReturn(List.of(
            GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now()),
            GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now())
        ));


        // when
        ReadCommentGroup readCommentGroup = onceGroupServiceImpl.readCommentInGroup(testUserEntity, true, 1L);

        // then
        Assertions.assertThat(readCommentGroup.comments()).hasSize(2); // 2개의 공개 댓글
        Assertions.assertThat(commentEntities).hasSize(2); // 중간 결과 검증
    }

    @Test
    @DisplayName("비공개 댓글 조회")
    void readCommentInGroupFalseCase() {

        // given
        OnceGroupEntity build = OnceGroupFixture.getTestOnceGroupEntity();
        build.addComment(CommentFixture.getTestCommentIsPublic(true));  // 공개 댓글
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글 1
        build.addComment(CommentFixture.getTestCommentIsPublic(false)); // 비공개 댓글 2

        List<CommentEntity> commentEntities = build.getComments().stream()
            .filter(c -> !c.isPublic()).toList(); // 필터링된 비공개 댓글

        UserEntity testUserEntity = UserEntityFixture.create();

        when(onceGroupRepository.findById(1L)).thenReturn(Optional.of(build));
        when(groupCommentVoMaker.makeByOnceGroup(eq(testUserEntity), eq(commentEntities), eq(build))).thenReturn(List.of(
            GroupCommentVoFixture.createGroupCommentEveryVo(1L,  LocalDateTime.now()),
            GroupCommentVoFixture.createGroupCommentEveryVo(1L, LocalDateTime.now())
        ));


        // when
        ReadCommentGroup readCommentGroup = onceGroupServiceImpl.readCommentInGroup(testUserEntity,
            false, 1L);

        // then
        Assertions.assertThat(readCommentGroup.comments()).hasSize(2); // 2개의 비공개 댓글
        Assertions.assertThat(commentEntities).hasSize(2); // 필터링된 비공개 댓글 확인

    }

    @Test
    @DisplayName("댓글 조회시 그룹 발견하지 못한 경우")
    void readCommentInGroupGroupNotFound() {
        // given
        when(onceGroupRepository.findById(99L)).thenReturn(Optional.empty());
        UserEntity userEntity = UserEntityFixture.create();


        // when & then
        Assertions.assertThatThrownBy(() -> onceGroupServiceImpl.readCommentInGroup(userEntity, true, 99L))
            .isInstanceOf(GongBaekException.class)
            .hasMessage(ResponseError.GROUP_NOT_FOUND.getMessage());
    }


}