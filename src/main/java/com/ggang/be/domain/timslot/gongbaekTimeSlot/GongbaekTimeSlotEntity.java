package com.ggang.be.domain.timslot.gongbaekTimeSlot;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "gongbaek_time_slot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GongbaekTimeSlotEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    private double startTime;
    private double endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    private GongbaekTimeSlotEntity(
            WeekDay weekDay, double startTime, double endTime, UserEntity userEntity
    ) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEntity = userEntity;
    }

    public void removeUserEntity() {
        this.userEntity = null;
    }
}
