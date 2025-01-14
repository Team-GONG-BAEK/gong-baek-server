package com.ggang.be.domain.userEveryGroup.application;


import com.ggang.be.api.group.everyGroup.service.UserEveryGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userEveryGroup.infra.UserEveryGroupRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserEveryGroupServiceImpl implements UserEveryGroupService {

    private final UserEveryGroupRepository userEveryGroupRepository;
    private final GroupVoMaker groupVoMaker;

    @Override
    public List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto) {
        List<UserEveryGroupEntity> userEveryGroupEntityById = userEveryGroupRepository.findUserEveryGroupEntityByEveryGroupEntity(
            dto.everyGroupEntity());

        return userEveryGroupEntityById.stream().map(this::makeUserEveryFillMemberResponse)
            .toList();
    }

    @Override
    public ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status) {
        List<UserEveryGroupEntity> userEveryGroupEntities
            = userEveryGroupRepository.findByUserEntity_id(currentUser.getId());

        return ReadEveryGroup.of(
            groupVoMaker.makeEveryGroup(getGroupsByStatus(userEveryGroupEntities, status)));
    }

    @Override
    public List<EveryGroupEntity> getGroupsByStatus(
        List<UserEveryGroupEntity> userEveryGroupEntities, boolean status) {
        if (status) {
            return userEveryGroupEntities.stream()
                .map(UserEveryGroupEntity::getEveryGroupEntity)
                .filter(everyGroupEntity -> everyGroupEntity.getStatus().isActive())
                .collect(Collectors.toList());
        } else {
            return userEveryGroupEntities.stream()
                .map(UserEveryGroupEntity::getEveryGroupEntity)
                .filter(everyGroupEntity -> everyGroupEntity.getStatus().isClosed())
                .collect(Collectors.toList());
        }
    }

    private FillMember makeUserEveryFillMemberResponse(UserEveryGroupEntity ue) {
        EveryGroupEntity everyGroupEntity = ue.getEveryGroupEntity();
        UserEntity userEntity = ue.getUserEntity();
        boolean isHost = everyGroupEntity.getUserEntity().getNickname()
            .equals(userEntity.getNickname());
        return FillMember.of(userEntity, isHost);
    }

}
