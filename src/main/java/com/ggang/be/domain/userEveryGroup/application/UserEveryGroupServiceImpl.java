package com.ggang.be.domain.userEveryGroup.application;


import com.ggang.be.api.group.everyGroup.service.UserEveryGroupService;
import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userEveryGroup.infra.UserEveryGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserEveryGroupServiceImpl implements UserEveryGroupService {

    private final UserEveryGroupRepository userEveryGroupRepository;

    @Override
    public List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto) {
        List<UserEveryGroupEntity> userEveryGroupEntityById = userEveryGroupRepository.findUserEveryGroupEntityByEveryGroupEntity(
            dto.everyGroupEntity());

        return userEveryGroupEntityById.stream().map(this::makeUserEveryFillMemberResponse)
            .toList();
    }

    private FillMember makeUserEveryFillMemberResponse(UserEveryGroupEntity ue) {
        EveryGroupEntity everyGroupEntity = ue.getEveryGroupEntity();
        UserEntity userEntity = ue.getUserEntity();
        boolean isHost = everyGroupEntity.getUserEntity().getNickname()
            .equals(userEntity.getNickname());
        return FillMember.of(userEntity, isHost);
    }

}
