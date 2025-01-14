package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.user.UserEntity;

import java.util.List;

public interface EveryGroupService {
    EveryGroupDetail getEveryGroupDetail(final long groupId, UserEntity userEntity);

    long getEveryGroupRegisterUserId(final long groupId);

    ReadEveryGroup getMyRegisteredGroups(UserEntity currentUser, boolean status);

    List<EveryGroupEntity> getGroupsByStatus(List<EveryGroupEntity> everyGroupEntities, boolean status);

    EveryGroupEntity findEveryGroupEntityByGroupId(long groupId);

    void writeCommentInGroup(CommentEntity commentEntity, final long groupId);

    ReadCommentGroup readCommentInGroup(boolean commentEntity, final long groupId);

    Long registerEveryGroup(RegisterGroupServiceRequest serviceRequest,
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity);

    void isExistedInTime(RegisterGroupServiceRequest serviceRequest);
}
