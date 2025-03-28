package com.ggang.be.domain.group.everyGroup;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity(name = "every_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class EveryGroupEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "every_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "everyGroupEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEveryGroupEntity> participantUsers = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "every_group_id")
    private List<CommentEntity> comments;

    @Column(nullable = false)
    private LocalDate dueDate;

    @OneToOne
    @JoinColumn(name = "gongbaek_time_slot_id")
    private GongbaekTimeSlotEntity gongbaekTimeSlotEntity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private int coverImg;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private int maxPeopleCount;
    private int currentPeopleCount;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String title;

    @Builder
    private EveryGroupEntity(UserEntity userEntity,
        List<CommentEntity> comments, LocalDate dueDate,
        Category category, int coverImg, String location, Status status,
        int maxPeopleCount, int currentPeopleCount, String introduction, String title, GongbaekTimeSlotEntity gongbaekTimeSlotEntity
    ) {
        this.userEntity = userEntity;
        this.comments = comments;
        this.dueDate = dueDate;
        this.gongbaekTimeSlotEntity = gongbaekTimeSlotEntity;
        this.category = category;
        this.coverImg = coverImg;
        this.location = location;
        this.status = status;
        this.maxPeopleCount = maxPeopleCount;
        this.currentPeopleCount = currentPeopleCount;
        this.introduction = introduction;
        this.title = title;
    }

    public boolean isHost(UserEntity currentUser) {
        if (this.userEntity == null) return false;
        return this.userEntity.getId().equals(currentUser.getId());
    }

    public boolean isApply(UserEntity currentUser) {
        return this.participantUsers.stream()
                .anyMatch(participant -> participant.getUserEntity().getId().equals(currentUser.getId()));
    }

    public void addComment(CommentEntity commentEntity) {
        this.comments.add(commentEntity);
    }

    public void addCurrentPeopleCount() {
        this.currentPeopleCount++;
        checkCurrentStatus(this.currentPeopleCount, this.maxPeopleCount);
    }

    public void decreaseCurrentPeopleCount() {
        this.currentPeopleCount--;
        checkCurrentStatus(this.currentPeopleCount, this.maxPeopleCount);
    }

    private void checkCurrentStatus(long currentPeopleCount, long maxPeopleCount){
        if(currentPeopleCount < maxPeopleCount) {
            this.status = Status.RECRUITING;
        }
        else this.status = Status.RECRUITED;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void removeHost() {
        this.userEntity = null;
    }

    public void closeGroup() {
        this.status = Status.CLOSED;
    }
}
