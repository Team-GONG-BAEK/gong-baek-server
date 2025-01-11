package com.ggang.be.domain.everyGroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.CommentFixture;
import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.everyGroup.infra.EveryGroupRepository;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ExtendWith(MockitoExtension.class)
class EveryGroupServiceImplTest {

    @Mock
    private EveryGroupRepository everyGroupRepository;

    @InjectMocks
    private EveryGroupServiceImpl everyGroupServiceImpl;

    @Test
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
}