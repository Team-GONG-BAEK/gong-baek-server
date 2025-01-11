package com.ggang.be.domain.group;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GroupCommentVoMaker {

    public List<GroupCommentVo> makeByEveryGroup(List<CommentEntity> commentEntities, EveryGroupEntity everyGroupEntity) {
        return commentEntities.stream()
            .sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed())
            .map(c -> GroupCommentVo.ofEveryGroup(everyGroupEntity, c))
            .toList();
    }

    public List<GroupCommentVo> makeByOnceGroup(List<CommentEntity> commentEntities, OnceGroupEntity onceGroupEntity) {
        return commentEntities.stream()
            .sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed())
            .map(c -> GroupCommentVo.ofOnceGroup(onceGroupEntity, c))
            .toList();
    }
}
