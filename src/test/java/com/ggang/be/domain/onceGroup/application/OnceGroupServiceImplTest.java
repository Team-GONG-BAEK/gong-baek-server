package com.ggang.be.domain.onceGroup.application;

import static org.mockito.Mockito.when;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.CommentFixture;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.onceGroup.infra.OnceGroupRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OnceGroupServiceImplTest {

    @Mock
    private OnceGroupRepository onceGroupRepository;

    @InjectMocks
    private OnceGroupServiceImpl onceGroupServiceImpl;

    @Test
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


}