package com.ggang.be.domain.everyGroup;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class EveryGroupService {
    private final EveryGroupRepository everyGroupRepository;

    public EveryGroupService(EveryGroupRepository everyGroupRepository) {
        this.everyGroupRepository = everyGroupRepository;
    }

    public GroupResponse getEveryGroupDetail(final long groupId, UserEntity currentUser) {
        EveryGroupEntity everyGroupEntity = findIdOrThrow(groupId);

        return new GroupResponse(
                everyGroupEntity.getId(),
                "ONCE",
                everyGroupEntity.getTitle(),
                everyGroupEntity.getLocation(),
                everyGroupEntity.getStatus().isActive(),
                everyGroupEntity.isHost(currentUser),
                everyGroupEntity.isApply(currentUser),
                everyGroupEntity.getCurrentPeopleCount(),
                everyGroupEntity.getMaxPeopleCount(),
                everyGroupEntity.getIntroduction(),
                everyGroupEntity.getCategory().toString(),
                everyGroupEntity.getCoverImg(),
                null,
                everyGroupEntity.getWeekDate().toString(),
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
