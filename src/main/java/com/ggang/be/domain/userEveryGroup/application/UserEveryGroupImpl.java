package com.ggang.be.domain.userEveryGroup.application;

import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.infra.UserEveryGroupRepository;
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
public class UserEveryGroupImpl implements UserEveryGroupService {

    private final UserEveryGroupRepository userEveryGroupRepository;
    private final GroupVoMaker groupVoMaker;

    @Override
    public ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<UserEveryGroupEntity> userEveryGroupEntities
                = userEveryGroupRepository.findByUserEntity_id(currentUser.getId());

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(getGroupsByStatus(userEveryGroupEntities, status)));
    }

    @Override
    public List<EveryGroupEntity> getGroupsByStatus(List<UserEveryGroupEntity> userEveryGroupEntities, boolean status) {
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

}
