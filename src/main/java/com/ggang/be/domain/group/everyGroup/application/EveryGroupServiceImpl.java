package com.ggang.be.domain.group.everyGroup.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.domain.group.GroupVoMaker;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.GroupCommentVoMaker;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDto;
import com.ggang.be.domain.group.everyGroup.infra.EveryGroupRepository;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.user.UserEntity;
import java.util.List;
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
public class EveryGroupServiceImpl implements EveryGroupService {

    private final EveryGroupRepository everyGroupRepository;
    private final GroupVoMaker groupVoMaker;

    private final GroupCommentVoMaker groupCommentVoMaker;

    @Override
    public EveryGroupDetail getEveryGroupDetail(final long groupId, final UserEntity userEntity) {
        EveryGroupEntity entity = findIdOrThrow(groupId);
        return EveryGroupDetail.toDto(entity, userEntity);
    }

    @Override
    public long getEveryGroupRegisterUserId(final long groupId) {
        EveryGroupEntity entity = findIdOrThrow(groupId);
        return entity.getUserEntity().getId();
    }

    @Override
    public ReadEveryGroup getMyRegisteredGroups(UserEntity currentUser, boolean status) {
        List<EveryGroupEntity> everyGroupEntities = everyGroupRepository.findByUserEntity_Id(currentUser.getId());

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(getGroupsByStatus(everyGroupEntities, status)));
    }

    @Override
    public ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status){
        List<EveryGroupEntity> everyGroupEntities
                = everyGroupRepository.findByUserEveryGroupEntities_UserEntity_Id(currentUser.getId());

        return ReadEveryGroup.of(groupVoMaker.makeEveryGroup(getGroupsByStatus(everyGroupEntities, status)));
    }

    @Override
    public List<EveryGroupEntity> getGroupsByStatus(List<EveryGroupEntity> everyGroupEntities, boolean status) {
        if (status) {
            return everyGroupEntities.stream()
                    .filter(group -> group.getStatus().isActive())
                    .collect(Collectors.toList());
        } else {
            return everyGroupEntities.stream()
                    .filter(group -> group.getStatus().isClosed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public EveryGroupEntity findEveryGroupEntityByGroupId(long groupId) {
        return everyGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void writeCommentInGroup(CommentEntity commentEntity, final long groupId) {
        everyGroupRepository.findById(groupId)
                .orElseThrow(() -> new GongBaekException(ResponseError.GROUP_NOT_FOUND))
                .addComment(commentEntity);
        log.info("everyGroupEntity add Comment success CommentId was  : {}", commentEntity.getId());
    }

    @Override
    public ReadCommentGroup readCommentInGroup(boolean isPublic, long groupId) {

        EveryGroupEntity everyGroupEntity = everyGroupRepository.findById(groupId)
                .orElseThrow(() -> new GongBaekException(ResponseError.GROUP_NOT_FOUND));

        List<CommentEntity> commentEntities = everyGroupEntity
                .getComments().stream().filter(c -> c.isPublic() == isPublic).toList();

        int commentCount = commentEntities.size();

        List<GroupCommentVo> onceGroupCommentVos = groupCommentVoMaker.makeByEveryGroup(
                commentEntities, everyGroupEntity);

        return ReadCommentGroup.of(commentCount, onceGroupCommentVos);

    }

    private EveryGroupEntity findIdOrThrow(final long groupId) {
        return everyGroupRepository.findById(groupId).orElseThrow(
            () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }

}
