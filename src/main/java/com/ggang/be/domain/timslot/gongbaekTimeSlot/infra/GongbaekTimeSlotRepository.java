package com.ggang.be.domain.timslot.gongbaekTimeSlot.infra;

import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GongbaekTimeSlotRepository extends JpaRepository<GongbaekTimeSlotEntity, Long> {
}
