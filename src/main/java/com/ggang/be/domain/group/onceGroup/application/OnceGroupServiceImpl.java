package com.ggang.be.domain.group.onceGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.group.onceGroup.infra.OnceGroupRepository;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnceGroupServiceImpl implements OnceGroupService {
    private final OnceGroupRepository onceGroupRepository;
    private final GroupVoMaker groupVoMaker;

    @Override
    public OnceGroupDto getOnceGroupDetail(final long groupId, final UserEntity userEntity) {
        OnceGroupEntity onceGroupEntity = findIdOrThrow(groupId);
        return OnceGroupDto.toDto(onceGroupEntity, userEntity);
    }

    @Override
    public long getOnceGroupRegisterUserId(final long groupId){
        OnceGroupEntity entity = findIdOrThrow(groupId);
        return entity.getUserEntity().getId();
    }

    @Override
    public ReadOnceGroup getMyRegisteredGroups(UserEntity currentUser, boolean status) {
        List<OnceGroupEntity> onceGroupEntities = onceGroupRepository.findByUserEntity_Id(currentUser.getId());

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(getGroupsByStatus(onceGroupEntities, status)));
    }

    @Override
    public ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<OnceGroupEntity> onceGroupEntities
                = onceGroupRepository.findByParticipantUsers_UserEntity_Id(currentUser.getId());

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(getGroupsByStatus(onceGroupEntities, status)));
    }

    @Override
    public List<OnceGroupEntity> getGroupsByStatus(List<OnceGroupEntity> onceGroupEntities, boolean status){
        if (status) {
            return onceGroupEntities.stream()
                    .filter(group -> group.getStatus().isActive())
                    .collect(Collectors.toList());
        } else {
            return onceGroupEntities.stream()
                    .filter(group -> group.getStatus().isClosed())
                    .collect(Collectors.toList());
        }
    }

    private OnceGroupEntity findIdOrThrow(final long groupId){
        return onceGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
