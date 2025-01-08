package com.ggang.be.domain.everyGroup;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import org.springframework.stereotype.Service;

@Service
public class EveryGroupService {
    private final EveryGroupRepository everyGroupRepository;

    public EveryGroupService(EveryGroupRepository everyGroupRepository) {
        this.everyGroupRepository = everyGroupRepository;
    }

    public GroupResponse getEveryGroupDetail(final long groupId) {
        EveryGroupEntity everyGroupEntity = findIdOrThrow(groupId);
        return new GroupResponse(
                everyGroupEntity.getId(),
                "WEEKLY",
                everyGroupEntity.getTitle(),
                everyGroupEntity.getLocation(),
                everyGroupEntity.getStatus().isActive(),
                everyGroupEntity.getCurrentPeopleCount(),
                everyGroupEntity.getMaxPeopleCount(),
                everyGroupEntity.getIntroduction(),
                everyGroupEntity.getCategory().toString(),
                everyGroupEntity.getWeekDate(),
                null,
                everyGroupEntity.getStartTime(),
                everyGroupEntity.getEndTime()
        );
    }

    private EveryGroupEntity findIdOrThrow(final long groupId){
        return everyGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
