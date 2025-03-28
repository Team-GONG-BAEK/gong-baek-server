package com.ggang.be.domain.timslot.gongbaekTimeSlot.infra;

import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GongbaekTimeSlotRepository extends JpaRepository<GongbaekTimeSlotEntity, Long> {
    List<GongbaekTimeSlotEntity> findByUserEntity_Id(Long id);
}
