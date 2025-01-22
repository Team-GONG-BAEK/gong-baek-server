package com.ggang.be.domain.timslot.lectureTimeSlot;


import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "lecture_time_slot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureTimeSlotEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WeekDay weekDay;

    private double startTime;
    private double endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    private LectureTimeSlotEntity(WeekDay weekDay, double startTime, double endTime,
        UserEntity userEntity) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEntity = userEntity;
    }
}
