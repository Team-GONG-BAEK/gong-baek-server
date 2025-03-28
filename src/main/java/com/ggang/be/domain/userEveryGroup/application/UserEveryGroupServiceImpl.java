package com.ggang.be.domain.userEveryGroup.application;


import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.group.vo.NearestGroup;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userEveryGroup.infra.UserEveryGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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
    @Transactional
    public void applyEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity){
        UserEveryGroupEntity userEveryGroupEntity = UserEveryGroupEntity.builder()
                .userEntity(currentUser)
                .everyGroupEntity(everyGroupEntity)
                .build();

        userEveryGroupRepository.save(userEveryGroupEntity);
        everyGroupEntity.addCurrentPeopleCount();
    }

    @Override
    @Transactional
    public void cancelEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity){
        UserEveryGroupEntity userEveryGroupEntity
                = userEveryGroupRepository.findByUserEntityAndEveryGroupEntity(currentUser, everyGroupEntity)
                .orElseThrow(() -> new GongBaekException(ResponseError.GROUP_CANCEL_NOT_FOUND));
        userEveryGroupRepository.delete(userEveryGroupEntity);
        everyGroupEntity.decreaseCurrentPeopleCount();
        everyGroupEntity.getParticipantUsers().remove(userEveryGroupEntity);
    }

    @Override
    public void isUserInGroup(UserEntity findUserEntity, EveryGroupEntity findEveryGroupEntity) {
        if (userEveryGroupRepository.findByUserEntityAndEveryGroupEntity(findUserEntity, findEveryGroupEntity).isEmpty()) {
            log.error("User is not in group");
            throw new GongBaekException(ResponseError.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    public ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<UserEveryGroupEntity> userEveryGroupEntities = getMyEveryGroup(currentUser);

        List<EveryGroupEntity> filteredGroups = getGroupsByStatus(userEveryGroupEntities, status).stream()
                .filter(group -> Optional.ofNullable(group.getUserEntity())
                        .map(host -> !host.getId().equals(currentUser.getId()))
                        .orElse(true))
                .toList();

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(filteredGroups));
    }

    @Override
    public NearestGroup getMyNearestGroup(UserEntity currentUser){
        List<UserEveryGroupEntity> userEveryGroupEntities = getMyEveryGroup(currentUser);

        EveryGroupEntity nearestGroup = getNearestGroup(getGroupsByStatus(userEveryGroupEntities, true));

        if (nearestGroup == null) return null;

        return NearestGroup.fromEveryEntity(nearestGroup);
    }

    @Override
    public void isValidCommentAccess(UserEntity userEntity, final long groupId) {
        List<UserEveryGroupEntity> userEveryGroupEntities = userEveryGroupRepository.findAllByUserEntity(userEntity);

        if(userEveryGroupEntities.stream().noneMatch(ue -> ue.getEveryGroupEntity().getId().equals(groupId)))
            throw new GongBaekException(ResponseError.UNAUTHORIZED_ACCESS);

    }

    @Override
    public void deleteUserEveryGroup(UserEntity user){
        userEveryGroupRepository.deleteAll(user.getUserEveryGroupEntities());
    }

    private EveryGroupEntity getNearestGroup(List<EveryGroupEntity> groups) {
        return groups.stream()
                .min(Comparator.comparing(group -> WeekDay.getNextMeetingDate(group.getGongbaekTimeSlotEntity().getWeekDay())))
                .orElse(null);
    }

    private List<UserEveryGroupEntity> getMyEveryGroup(UserEntity currentUser){
        return userEveryGroupRepository.findByUserEntity_id(currentUser.getId());
    }

    private List<EveryGroupEntity> getGroupsByStatus(List<UserEveryGroupEntity> userEveryGroupEntities, boolean status) {
        return userEveryGroupEntities.stream()
                .map(UserEveryGroupEntity::getEveryGroupEntity)
                .filter(group -> (status && group.getStatus().isActive()) || (!status && group.getStatus().isClosed()))
                .collect(Collectors.toList());
    }

    private FillMember makeUserEveryFillMemberResponse(UserEveryGroupEntity ue) {
        EveryGroupEntity everyGroupEntity = ue.getEveryGroupEntity();
        UserEntity userEntity = ue.getUserEntity();
        boolean isHost = everyGroupEntity.getUserEntity().getNickname()
            .equals(userEntity.getNickname());
        return FillMember.of(userEntity, isHost);
    }
}
