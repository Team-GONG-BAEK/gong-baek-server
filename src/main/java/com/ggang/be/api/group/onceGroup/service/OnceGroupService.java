package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import java.util.List;

public interface OnceGroupService {
    OnceGroupDto getOnceGroupDetail(final long groupId, UserEntity user);

    long getOnceGroupRegisterUserId(final long groupId);

    ReadOnceGroup getMyRegisteredGroups(UserEntity currentUser, boolean status);

    OnceGroupEntity findOnceGroupEntityByGroupId(long groupId);

    void writeCommentInGroup(CommentEntity commentEntity, final long groupId);

    ReadCommentGroup readCommentInGroup(UserEntity userEntity, boolean isPublic, final long groupId);

    Long registerOnceGroup(RegisterGroupServiceRequest serviceRequest,
                           GongbaekTimeSlotEntity gongbaekTimeSlotEntity);

    void isExistInOnceGroupTimeSlot(RegisterGroupServiceRequest serviceRequest);

    ReadOnceGroup getActiveOnceGroups(UserEntity currentUser);

}
