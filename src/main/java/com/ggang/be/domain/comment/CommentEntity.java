package com.ggang.be.domain.comment;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private boolean isPublic;

    @Column(nullable = false)
    @Lob
    private String body;

    @Builder
    private CommentEntity(UserEntity userEntity, boolean isPublic, String body) {
        this.userEntity = userEntity;
        this.isPublic = isPublic;
        this.body = body;
    }
}
