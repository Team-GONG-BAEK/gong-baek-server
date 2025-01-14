package com.ggang.be.domain.group.onceGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity(name = "once_group")
public class OnceGroupEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "once_group_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "onceGroupEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserOnceGroupEntity> participantUsers = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "once_group_id")
    private List<CommentEntity> comments;

    @Column(nullable = false)
    private LocalDate groupDate;

    private double startTime;
    private double endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private int coverImg;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Status status;

    private int maxPeopleCount;
    private int currentPeopleCount;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String title;

    public boolean isHost(UserEntity currentUser) {
        return this.userEntity.getId().equals(currentUser.getId());
    }

    public boolean isApply(UserEntity currentUser) {
        return this.participantUsers.stream()
                .anyMatch(participant -> participant.getUserEntity().getId().equals(currentUser.getId()));
    }
}
