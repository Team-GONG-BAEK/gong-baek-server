package com.ggang.be.domain.userEveryGroup.dto;

import com.ggang.be.domain.user.UserEntity;

public record FillMember(int profileImg, boolean isHost, String nickname ) {

    public static FillMember of(UserEntity userEntity, boolean isHost) {
        return new FillMember(userEntity.getProfileImg(), isHost, userEntity.getNickname());
    }
}
