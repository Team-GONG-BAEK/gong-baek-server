package com.ggang.be.domain.userOnceGroup.application;

import com.ggang.be.api.group.onceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import com.ggang.be.domain.userOnceGroup.infra.UserOnceGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOnceGroupServiceImpl implements UserOnceGroupService {

    private final UserOnceGroupRepository userOnceGroupRepository;

    @Override
    public List<FillMember> getOnceGroupUsersInfo(ReadOnceGroupMember dto) {
        List<UserOnceGroupEntity> userOnceGroupEntityByGroupId = userOnceGroupRepository.findUserOnceGroupEntityByOnceGroupEntity(
            dto.onceGroupEntity());

        return userOnceGroupEntityByGroupId.stream().map(this::makeUserOnceFillMemberResponse)
            .toList();
    }


    private FillMember makeUserOnceFillMemberResponse(UserOnceGroupEntity ue) {
        OnceGroupEntity onceGroupEntity = ue.getOnceGroupEntity();
        UserEntity userEntity = ue.getUserEntity();
        boolean isHost = onceGroupEntity.getUserEntity().getNickname().equals(userEntity.getNickname());
        return FillMember.of(userEntity, isHost);
    }


}
