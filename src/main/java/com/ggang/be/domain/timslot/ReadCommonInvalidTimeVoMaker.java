package com.ggang.be.domain.timslot;

import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.vo.ReadCommonInvalidTimeVo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ReadCommonInvalidTimeVoMaker {
    public List<ReadCommonInvalidTimeVo> convertToCommonResponse(
        List<LectureTimeSlotEntity> lectureTimeSlotEntities) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return lectureTimeSlotEntities.stream()
            .sorted(Comparator.comparing(LectureTimeSlotEntity::getWeekDate))
            .map(entity -> makeReadCommonInvalidTimeVo(entity, atomicInteger))
            .sorted(Comparator.comparing(ReadCommonInvalidTimeVo::idx)).toList();
    }

    private ReadCommonInvalidTimeVo makeReadCommonInvalidTimeVo(LectureTimeSlotEntity entity,
        AtomicInteger atomicInteger) {
        return ReadCommonInvalidTimeVo.fromLectureEntity(atomicInteger.getAndIncrement(),
            entity);
    }
}
