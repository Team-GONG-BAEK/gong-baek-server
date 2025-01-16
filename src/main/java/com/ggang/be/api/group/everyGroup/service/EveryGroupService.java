package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public interface EveryGroupService {
    EveryGroupDetail getEveryGroupDetail(final long groupId, UserEntity userEntity);

    long getEveryGroupRegisterUserId(final long groupId);

    ReadEveryGroup getMyRegisteredGroups(UserEntity currentUser, boolean status);

    EveryGroupEntity findEveryGroupEntityByGroupId(long groupId);

    void writeCommentInGroup(CommentEntity commentEntity, final long groupId);

    ReadCommentGroup readCommentInGroup(UserEntity userEntity, boolean commentEntity, final long groupId);

    Long registerEveryGroup(RegisterGroupServiceRequest serviceRequest,
                            GongbaekTimeSlotEntity gongbaekTimeSlotEntity);

    ReadEveryGroup getActiveEveryGroups(UserEntity currentUser);

    void validateApplyEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity);

    boolean validateCancelEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity);
}
