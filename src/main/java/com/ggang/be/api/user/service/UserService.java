package com.ggang.be.api.user.service;

import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.domain.user.dto.UserSchoolDto;

public interface UserService {
  
    UserEntity getUserById(Long userId);

    UserSchoolDto getUserSchoolById(Long userId);

    boolean duplicateCheckNickname(String nickname);

    UserEntity saveUserBySignup(SaveUserSignUp request);
}
