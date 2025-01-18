package com.ggang.be.api.group;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentRegistry {

    private final List<CommentFacadeHandler> groupHandlers;

    public CommentFacadeHandler getGroupHandler(GroupType groupType) {
        return groupHandlers.stream()
            .filter(groupHandler -> groupHandler.supports(groupType))
            .findFirst()
            .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }


}
