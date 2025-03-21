package com.ggang.be.domain.userOnceGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user_once_group")
public class UserOnceGroupEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_once_group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "once_group_id")
    private OnceGroupEntity onceGroupEntity;


    @Builder
    private UserOnceGroupEntity(UserEntity userEntity, OnceGroupEntity onceGroupEntity) {
        this.userEntity = userEntity;
        this.onceGroupEntity = onceGroupEntity;
    }
}