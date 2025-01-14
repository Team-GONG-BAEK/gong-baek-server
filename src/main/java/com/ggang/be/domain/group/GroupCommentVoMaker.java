package com.ggang.be.domain.group;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import com.ggang.be.domain.user.UserEntity;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GroupCommentVoMaker {

    public List<GroupCommentVo> makeByEveryGroup(UserEntity userEntity, List<CommentEntity> commentEntities, EveryGroupEntity everyGroupEntity) {
        return commentEntities.stream()
            .sorted(Comparator.comparing(CommentEntity::getCreatedAt))
            .map(c -> GroupCommentVo.ofEveryGroup(userEntity, everyGroupEntity, c))
            .toList();
    }

    public List<GroupCommentVo> makeByOnceGroup(UserEntity userEntity, List<CommentEntity> commentEntities, OnceGroupEntity onceGroupEntity) {
        return commentEntities.stream()
            .sorted(Comparator.comparing(CommentEntity::getCreatedAt))
            .map(c -> GroupCommentVo.ofOnceGroup(userEntity, onceGroupEntity, c))
            .toList();
    }
}
