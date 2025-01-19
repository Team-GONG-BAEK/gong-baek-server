package com.ggang.be.api.group;

import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupVoAggregator {

    public static List<GroupVo> aggregateAndSort(List<EveryGroupVo> everyGroupResponses, List<OnceGroupVo> onceGroupResponses) {
        return Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup)
                )
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .collect(Collectors.toList());
    }
}
