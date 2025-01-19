package com.ggang.be.api.comment.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Registry
@RequiredArgsConstructor
public class CommentStrategyRegistry {

    private final List<CommentStrategy> commentStrategies;

    public CommentStrategy getCommentGroupStrategy(GroupType groupType) {
        return commentStrategies.stream()
            .filter(strategy -> strategy.supports(groupType))
            .findFirst()
            .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }


}
