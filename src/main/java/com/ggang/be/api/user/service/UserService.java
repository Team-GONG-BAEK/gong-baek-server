package com.ggang.be.api.user.service;

import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.domain.user.dto.UserSchoolDto;

public interface UserService {
    UserEntity getUserById(Long userId);

    UserSchoolDto getUserSchoolById(Long userId);

    boolean duplicateCheckNickname(String nickname);

    UserEntity saveUserBySignup(SaveUserSignUp request);

    void validateRefreshToken(UserEntity findUser, String refreshToken);

    void updateRefreshToken(String refreshToken, UserEntity userEntity);

    boolean findByPlatformAndPlatformId(Platform platform, String platformId);

    Long getUserIdByPlatformAndPlatformId(Platform platform, String platformId);

}
