package com.ggang.be.api.user.service;

import com.ggang.be.domain.user.UserEntity;

public interface UserService {
    UserEntity getUserById(Long userId);

    boolean duplicateCheckNickname(String nickname);

}
