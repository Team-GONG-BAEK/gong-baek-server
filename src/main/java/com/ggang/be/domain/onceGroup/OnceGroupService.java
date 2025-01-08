package com.ggang.be.domain.onceGroup;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class OnceGroupService {
    private final OnceGroupRepository onceGroupRepository;

    public OnceGroupService(OnceGroupRepository onceGroupRepository) {
        this.onceGroupRepository = onceGroupRepository;
    }

    public GroupResponse getOnceGroupDetail(final long groupId, UserEntity currentUser) {
        OnceGroupEntity onceGroupEntity = findIdOrThrow(groupId);

        return new GroupResponse(
                onceGroupEntity.getId(),
                "ONCE",
                onceGroupEntity.getTitle(),
                onceGroupEntity.getLocation(),
                onceGroupEntity.getStatus().isActive(),
                onceGroupEntity.isHost(currentUser),
                onceGroupEntity.isApply(currentUser),
                onceGroupEntity.getCurrentPeopleCount(),
                onceGroupEntity.getMaxPeopleCount(),
                onceGroupEntity.getIntroduction(),
                onceGroupEntity.getCategory().toString(),
                onceGroupEntity.getCoverImg(),
                WeekDate.fromDayOfWeek(onceGroupEntity.getGroupDate().getDayOfWeek()),
                onceGroupEntity.getGroupDate().toString(),
                onceGroupEntity.getStartTime(),
                onceGroupEntity.getEndTime()
        );
    }

    private OnceGroupEntity findIdOrThrow(final long groupId){
        return onceGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
