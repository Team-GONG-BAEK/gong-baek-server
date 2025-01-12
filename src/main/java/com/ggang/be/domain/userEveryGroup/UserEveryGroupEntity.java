package com.ggang.be.domain.userEveryGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user_every_group")
public class UserEveryGroupEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_every_group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "every_group_id")
    private EveryGroupEntity everyGroupEntity;

}