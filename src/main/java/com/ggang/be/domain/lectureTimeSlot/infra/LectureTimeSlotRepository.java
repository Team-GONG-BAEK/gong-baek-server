package com.ggang.be.domain.lectureTimeSlot.infra;

import com.ggang.be.domain.lectureTimeSlot.LectureTimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureTimeSlotRepository extends JpaRepository<LectureTimeSlotEntity, Long> {

}
