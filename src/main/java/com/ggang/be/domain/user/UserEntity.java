package com.ggang.be.domain.user;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import jakarta.persistence.*;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user")
@Table(indexes = {
    @Index(name="user_nickname_index", columnList = "nickname")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "userEntity")
    private List<UserOnceGroupEntity> userOnceGroupEntites;

    @OneToMany(mappedBy = "userEntity")
    private List<UserEveryGroupEntity> userEveryGroupEntities;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @Column(nullable = false)
    private String schoolMajorName;

    private int profileImg;

    @Column(nullable = false)
    private String nickname;

    private int schoolGrade;

    private int enterYear;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mbti mbti;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    @OneToMany(mappedBy = "userEntity")
    private List<GongbaekTimeSlotEntity> gongbaekTimeSlotEntities;

    @Column(nullable = false)
    @OneToMany(mappedBy = "userEntity")
    private List<LectureTimeSlotEntity> lectureTimeSlotEntities;


    @Builder
    private UserEntity(SchoolEntity school, String schoolMajorName, int profileImg, String nickname,
        int schoolGrade, int enterYear, Mbti mbti, Gender gender, String introduction) {
        this.school = school;
        this.schoolMajorName = schoolMajorName;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.schoolGrade = schoolGrade;
        this.enterYear = enterYear;
        this.mbti = mbti;
        this.gender = gender;
        this.introduction = introduction;
    }
}
