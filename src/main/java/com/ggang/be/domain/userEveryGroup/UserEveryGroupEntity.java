package com.ggang.be.domain.userEveryGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
