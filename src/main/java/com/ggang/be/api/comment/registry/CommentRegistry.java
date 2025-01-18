package com.ggang.be.api.comment.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Registry
@RequiredArgsConstructor
public class CommentRegistry {

    private final List<CommentFacadeHandler> groupHandlers;

    public CommentFacadeHandler getCommentGroupHandler(GroupType groupType) {
        return groupHandlers.stream()
            .filter(groupHandler -> groupHandler.supports(groupType))
            .findFirst()
            .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }


}
