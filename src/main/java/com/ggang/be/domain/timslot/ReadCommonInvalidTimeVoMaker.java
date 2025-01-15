package com.ggang.be.domain.timslot;

import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.vo.ReadCommonInvalidTimeVo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ReadCommonInvalidTimeVoMaker {
    public List<ReadCommonInvalidTimeVo> convertToCommonResponse(
        List<LectureTimeSlotEntity> lectureTimeSlotEntities) {
        return lectureTimeSlotEntities.stream().map(ReadCommonInvalidTimeVo::fromLectureEntity)
            .sorted(Comparator.comparing(ReadCommonInvalidTimeVo::weekDate)).toList();
    }
}
