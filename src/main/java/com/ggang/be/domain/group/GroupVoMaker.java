package com.ggang.be.domain.group;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class GroupVoMaker {
    public List<EveryGroupVo> makeEveryGroup(List<EveryGroupEntity> everyGroupEntities) {
        return everyGroupEntities.stream()
                .sorted(Comparator.comparing(EveryGroupEntity::getCreatedAt).reversed())
                .map(EveryGroupVo::of)
                .toList();
    }

    public List<OnceGroupVo> makeOnceGroup(List<OnceGroupEntity> onceGroupEntities) {
        return onceGroupEntities.stream()
                .sorted(Comparator.comparing(OnceGroupEntity::getCreatedAt).reversed())
                .map(OnceGroupVo::of)
                .toList();
    }
}