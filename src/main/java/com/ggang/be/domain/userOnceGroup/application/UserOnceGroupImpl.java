package com.ggang.be.domain.userOnceGroup.application;

import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import com.ggang.be.domain.userOnceGroup.infra.UserOnceGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserOnceGroupImpl implements UserOnceGroupService {

    private final UserOnceGroupRepository userOnceGroupRepository;
    private final GroupVoMaker groupVoMaker;

    @Override
    public ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<UserOnceGroupEntity> userOnceGroupEntities
                = userOnceGroupRepository.findByUserEntity_id(currentUser.getId());

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(getGroupsByStatus(userOnceGroupEntities, status)));
    }

    @Override
    public List<OnceGroupEntity> getGroupsByStatus(List<UserOnceGroupEntity> userOnceGroupEntities, boolean status) {
        if (status) {
            return userOnceGroupEntities.stream()
                    .map(UserOnceGroupEntity::getOnceGroupEntity)
                    .filter(everyGroupEntity -> everyGroupEntity.getStatus().isActive())
                    .collect(Collectors.toList());
        } else {
            return userOnceGroupEntities.stream()
                    .map(UserOnceGroupEntity::getOnceGroupEntity)
                    .filter(everyGroupEntity -> everyGroupEntity.getStatus().isClosed())
                    .collect(Collectors.toList());
        }
    }

}
