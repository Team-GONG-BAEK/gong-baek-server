package com.ggang.be.api.group;

import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.global.util.TimeConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class GroupStatusUpdater {
    @Transactional
    public void updateOnceGroup(OnceGroupEntity entity){
        double startTime = entity.getGongbaekTimeSlotEntity().getStartTime();
        LocalTime localTime = TimeConverter.toLocalTime(startTime);
        LocalDateTime endRegisterTime = LocalDateTime.of(entity.getGroupDate(), localTime);
        if (LocalDateTime.now().isAfter(endRegisterTime))
            entity.updateStatus(Status.CLOSED);
    }

    @Transactional
    public void updateEveryGroup(EveryGroupEntity entity){
        LocalTime midNight = LocalTime.of(0, 0);
        LocalDateTime endRegisterTime = LocalDateTime.of(entity.getDueDate(), midNight);
        if (LocalDateTime.now().isAfter(endRegisterTime))
            entity.updateStatus(Status.CLOSED);
    }

}
