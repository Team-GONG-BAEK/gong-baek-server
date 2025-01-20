package com.ggang.be.api.group.dto;

import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import java.util.List;

public record CombinedGroupVos(
    List<EveryGroupVo> everyGroupVos,
    List<OnceGroupVo> onceGroupVos
) {

    public static CombinedGroupVos of(List<EveryGroupVo> everyGroupResponses, List<OnceGroupVo> onceGroupResponses) {
        return new CombinedGroupVos(everyGroupResponses, onceGroupResponses);
    }

}
