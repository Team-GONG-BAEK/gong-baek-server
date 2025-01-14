package com.ggang.be.domain.group.everyGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.group.everyGroup.infra.EveryGroupRepository;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EveryGroupServiceImpl implements EveryGroupService {
    private final EveryGroupRepository everyGroupRepository;
    private final GroupVoMaker groupVoMaker;


    @Override
    public EveryGroupDetail getEveryGroupDetail(final long groupId, final UserEntity userEntity) {
        EveryGroupEntity entity = findIdOrThrow(groupId);
        return EveryGroupDetail.toDto(entity, userEntity);
    }

    @Override
    public long getEveryGroupRegisterUserId(final long groupId){
        EveryGroupEntity entity = findIdOrThrow(groupId);
        return entity.getUserEntity().getId();
    }

    @Override
    public ReadEveryGroup getMyRegisteredGroups(UserEntity currentUser, boolean status) {
        List<EveryGroupEntity> everyGroupEntities = everyGroupRepository.findByUserEntity_Id(currentUser.getId());

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(getGroupsByStatus(everyGroupEntities, status)));
    }

    @Override
    public ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<EveryGroupEntity> everyGroupEntities
                = everyGroupRepository.findByUserEveryGroupEntities_UserEntity_Id(currentUser.getId());

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(getGroupsByStatus(everyGroupEntities, status)));
    }

    @Override
    public List<EveryGroupEntity> getGroupsByStatus(List<EveryGroupEntity> everyGroupEntities, boolean status) {
        if (status) {
            return everyGroupEntities.stream()
                    .filter(group -> group.getStatus().isActive())
                    .collect(Collectors.toList());
        } else {
            return everyGroupEntities.stream()
                    .filter(group -> group.getStatus().isClosed())
                    .collect(Collectors.toList());
        }
    }

    private EveryGroupEntity findIdOrThrow(final long groupId){
        return everyGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }

}
