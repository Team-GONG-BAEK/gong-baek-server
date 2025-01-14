package com.ggang.be.domain.group.onceGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.GroupCommentVoMaker;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.group.onceGroup.infra.OnceGroupRepository;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
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
public class OnceGroupServiceImpl implements OnceGroupService {

    private final OnceGroupRepository onceGroupRepository;
    private final GroupVoMaker groupVoMaker;
    private final GroupCommentVoMaker groupCommentVoMaker;

    @Override
    public OnceGroupDto getOnceGroupDetail(final long groupId, final UserEntity userEntity) {
        OnceGroupEntity onceGroupEntity = findIdOrThrow(groupId);
        return OnceGroupDto.toDto(onceGroupEntity, userEntity);
    }

    @Override
    public long getOnceGroupRegisterUserId(final long groupId) {
        OnceGroupEntity entity = findIdOrThrow(groupId);
        return entity.getUserEntity().getId();
    }

    @Override
    public ReadOnceGroup getMyRegisteredGroups(UserEntity currentUser, boolean status) {
        List<OnceGroupEntity> onceGroupEntities = onceGroupRepository.findByUserEntity_Id(currentUser.getId());

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(getGroupsByStatus(onceGroupEntities, status)));
    }

    @Override
    public ReadOnceGroup getActiveOnceGroups(UserEntity currentUser) {
        List<OnceGroupEntity> onceGroupEntities = onceGroupRepository.findAll();

        return ReadOnceGroup.of(groupVoMaker.makeOnceGroup(getRecruitingGroups(onceGroupEntities)));
    }

    private List<OnceGroupEntity> getRecruitingGroups(List<OnceGroupEntity> onceGroupEntities) {
        return onceGroupEntities.stream()
                .filter(group -> group.getStatus().isRecruiting())
                .collect(Collectors.toList());
    }

    private List<OnceGroupEntity> getGroupsByStatus(List<OnceGroupEntity> onceGroupEntities, boolean status){
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

    @Override
    public OnceGroupEntity findOnceGroupEntityByGroupId(long groupId) {
        return onceGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void writeCommentInGroup(CommentEntity commentEntity, final long groupId) {
        onceGroupRepository.findById(groupId)
                .orElseThrow(() -> new GongBaekException(ResponseError.GROUP_NOT_FOUND))
                .addComment(commentEntity);
        log.info("onceGroupEntity add Comment success CommentId was  : {}", commentEntity.getId());
    }

    @Override
    public ReadCommentGroup readCommentInGroup(boolean isPublic, long groupId) {

        OnceGroupEntity onceGroupEntity = onceGroupRepository.findById(groupId)
                .orElseThrow(() -> new GongBaekException(ResponseError.GROUP_NOT_FOUND));

        List<CommentEntity> commentEntities = onceGroupEntity
                .getComments().stream().filter(c -> c.isPublic() == isPublic).toList();

        int commentCount = commentEntities.size();

        List<GroupCommentVo> onceGroupCommentVos = groupCommentVoMaker.makeByOnceGroup(
                commentEntities, onceGroupEntity);

        return ReadCommentGroup.of(commentCount, onceGroupCommentVos);
    }

    @Override
    @Transactional
    public Long registerOnceGroup(RegisterGroupServiceRequest serviceRequest,
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity) {

        OnceGroupEntity buildEntity = buildOnceGroupEntity(
            serviceRequest, gongbaekTimeSlotEntity);

        return onceGroupRepository.save(buildEntity).getId();
    }

    @Override
    public void isExistInOnceGroupTimeSlot(RegisterGroupServiceRequest serviceRequest) {

        if(onceGroupRepository.isInTime
            (serviceRequest.userEntity(), serviceRequest.startTime(), serviceRequest.endTime(),
                serviceRequest.weekDate(), Status.CLOSED))
            throw new GongBaekException(ResponseError.GROUP_ALREADY_EXIST);
    }


    private OnceGroupEntity buildOnceGroupEntity(RegisterGroupServiceRequest serviceRequest,
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity) {
        return OnceGroupEntity.builder()
            .groupDate(serviceRequest.weekDate())
            .category(serviceRequest.category())
            .coverImg(serviceRequest.coverImg())
            .location(serviceRequest.location())
            .currentPeopleCount(1)
            .status(Status.RECRUITING)
            .maxPeopleCount(serviceRequest.maxPeopleCount())
            .title(serviceRequest.groupTitle())
            .gongbaekTimeSlotEntity(gongbaekTimeSlotEntity)
            .introduction(serviceRequest.introduction())
            .userEntity(serviceRequest.userEntity())
            .build();
    }

    private OnceGroupEntity findIdOrThrow(final long groupId) {
        return onceGroupRepository.findById(groupId).orElseThrow(
            () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
