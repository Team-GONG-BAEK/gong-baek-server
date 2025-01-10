package com.ggang.be.domain.userOnceGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

}