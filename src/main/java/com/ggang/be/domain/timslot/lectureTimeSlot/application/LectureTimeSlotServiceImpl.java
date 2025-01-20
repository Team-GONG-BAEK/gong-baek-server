package com.ggang.be.domain.timslot.lectureTimeSlot.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.lectureTimeSlot.dto.LectureTimeSlotRequest;
import com.ggang.be.domain.timslot.lectureTimeSlot.infra.LectureTimeSlotRepository;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureTimeSlotServiceImpl implements LectureTimeSlotService {
    private final LectureTimeSlotRepository lectureTimeSlotRepository;

    @Override
    @Transactional
    public void saveLectureTimeSlot(List<LectureTimeSlotVo> timeTableVos, UserEntity userEntity) {
        timeTableVos.stream()
                .map(lectureTimeSlotVo -> LectureTimeSlotVo.toEntity(lectureTimeSlotVo, userEntity))
                .forEach(lectureTimeSlotRepository::save);
    }

    @Override
    public void isExistInLectureTImeSlot(UserEntity findUserEntity, LectureTimeSlotRequest dto) {
        if(lectureTimeSlotRepository.isInTime(
                dto.startTime(), dto.endTime(), findUserEntity, dto.weekDate()
        ))
            throw new GongBaekException(ResponseError.TIME_SLOT_ALREADY_EXIST);
    }

    @Override
    public boolean isActiveGroupsInLectureTimeSlot(UserEntity findUserEntity, GroupVo groupVo) {
        return !lectureTimeSlotRepository.isInTime(
            groupVo.startTime(),
            groupVo.endTime(),
            findUserEntity,
            groupVo.weekDate());
    }

    @Override
    public List<LectureTimeSlotEntity> readUserTime(UserEntity userById) {
        return lectureTimeSlotRepository.findAllByUserEntity(
            userById);
    }

}
