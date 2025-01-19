package com.ggang.be.api.group.dto;

import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import java.util.List;

public record GroupResponsesDto(
    List<EveryGroupVo> everyGroupResponses,
    List<OnceGroupVo> onceGroupResponses
) {

    public static GroupResponsesDto of(List<EveryGroupVo> everyGroupResponses, List<OnceGroupVo> onceGroupResponses) {
        return new GroupResponsesDto(everyGroupResponses, onceGroupResponses);
    }

}
