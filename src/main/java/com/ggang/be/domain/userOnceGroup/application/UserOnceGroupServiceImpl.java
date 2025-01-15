package com.ggang.be.domain.userOnceGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;
import com.ggang.be.domain.userOnceGroup.infra.UserOnceGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserOnceGroupServiceImpl implements UserOnceGroupService {
    private final UserOnceGroupRepository userOnceGroupRepository;
    private final GroupVoMaker groupVoMaker;

    @Override
    public List<FillMember> getOnceGroupUsersInfo(ReadOnceGroupMember dto) {
        List<UserOnceGroupEntity> userOnceGroupEntityByGroupId = userOnceGroupRepository.findUserOnceGroupEntityByOnceGroupEntity(
            dto.onceGroupEntity());

        return userOnceGroupEntityByGroupId.stream().map(this::makeUserOnceFillMemberResponse)
            .toList();
    }

    @Override
    public ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status) {
        List<UserOnceGroupEntity> userOnceGroupEntities = getMyOnceGroup(currentUser);

        List<OnceGroupEntity> filteredGroups = getGroupsByStatus(userOnceGroupEntities,
            status).stream()
            .filter(group -> !group.getUserEntity().getId().equals(currentUser.getId()))
            .toList();

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(filteredGroups));
    }

    @Override
    public NearestOnceGroup getMyNearestOnceGroup(UserEntity currentUser) {
        List<UserOnceGroupEntity> userOnceGroupEntities = getMyOnceGroup(currentUser);

        OnceGroupEntity nearestGroup = getNearestGroup(getGroupsByStatus(userOnceGroupEntities, true));

        if (nearestGroup == null) return null;

        return NearestOnceGroup.toDto(nearestGroup);
    }

    @Override
    public List<UserOnceGroupEntity> readUserTIme(UserEntity findUserEntity) {
        return userOnceGroupRepository.findAllByUserEntity(findUserEntity);
    }

    @Override
    @Transactional
    public void applyOnceGroup(UserEntity currentUser, OnceGroupEntity onceGroupEntity){
        UserOnceGroupEntity userOnceGroupEntity = UserOnceGroupEntity.builder()
                .userEntity(currentUser)
                .onceGroupEntity(onceGroupEntity)
                .build();

        userOnceGroupRepository.save(userOnceGroupEntity);
        onceGroupEntity.addCurrentPeopleCount();
    }

    @Override
    public void isValidCommentAccess(UserEntity userEntity, long groupId) {
        List<UserOnceGroupEntity> userOnceGroupEntities = userOnceGroupRepository.findAllByUserEntity(
            userEntity);

        if (userOnceGroupEntities.stream()
            .noneMatch(ue -> ue.getOnceGroupEntity().getId() == groupId)) {
            throw new GongBaekException(ResponseError.UNAUTHORIZED_ACCESS);
        }

    }

    private OnceGroupEntity getNearestGroup(List<OnceGroupEntity> groups) {
        return groups.stream()
            .min(Comparator.comparing(OnceGroupEntity::getGroupDate))
            .orElse(null);
    }

    private List<UserOnceGroupEntity> getMyOnceGroup(UserEntity currentUser) {
        return userOnceGroupRepository.findByUserEntity_id(currentUser.getId());
    }

    private List<OnceGroupEntity> getGroupsByStatus(List<UserOnceGroupEntity> userOnceGroupEntities,
        boolean status) {
        return userOnceGroupEntities.stream()
            .map(UserOnceGroupEntity::getOnceGroupEntity)
            .filter(group -> filterByStatus(group, status))
            .collect(Collectors.toList());
    }

    private FillMember makeUserOnceFillMemberResponse(UserOnceGroupEntity ue) {
        OnceGroupEntity onceGroupEntity = ue.getOnceGroupEntity();
        UserEntity userEntity = ue.getUserEntity();
        boolean isHost = onceGroupEntity.getUserEntity().getNickname()
            .equals(userEntity.getNickname());
        return FillMember.of(userEntity, isHost);
    }

    private boolean filterByStatus(OnceGroupEntity group, boolean status) {
        return (status && group.getStatus().isActive()) || (!status && group.getStatus()
            .isClosed());
    }
}
