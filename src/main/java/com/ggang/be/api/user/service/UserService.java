package com.ggang.be.api.user.service;

import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;

public interface UserService {
    boolean duplicateCheckNickname(String nickname);

    UserEntity saveUserBySignup(SaveUserSignUp request);
}
