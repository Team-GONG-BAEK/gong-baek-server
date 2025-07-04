package com.ggang.be.domain.user.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.domain.user.dto.UserProfile;
import com.ggang.be.domain.user.dto.UserSchoolDto;
import com.ggang.be.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppProperties appProperties;

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));
    }

    @Override
    public UserSchoolDto getUserSchoolById(Long userId) {
        String nickname = userRepository.findNicknameById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));
        String schoolName = userRepository.findSchoolNameById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));

        return new UserSchoolDto(nickname, schoolName);
    }

    @Override
    public UserProfile getUserInfoById(Long userId) {
        return UserProfile.of(userRepository.findById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND))
        );
    }

    @Override
    public boolean duplicateCheckNickname(String nickname) {
        log.info("nickname {}", nickname);
        if (userRepository.existsUserEntitiesByNickname(nickname))
            throw new GongBaekException(ResponseError.NICKNAME_ALREADY_EXISTS);
        return true;
    }

    @Override
    @Transactional
    public UserEntity saveUserBySignup(SaveUserSignUp request) {
        UserEntity build = UserEntity.builder()
                .platform(request.platform())
                .platformId(request.platformUserId())
                .email(request.email())
                .nickname(request.nickname())
                .school(request.school())
                .gender(request.sex())
                .introduction(request.introduction())
                .mbti(request.mbti())
                .profileImg(request.profileImg())
                .enterYear(request.enterYear())
                .schoolMajorName(request.schoolMajorName())
                .build();

        log.info("userEntity {}", build);
        return userRepository.save(build);
    }

    @Override
    public void validateRefreshToken(UserEntity findUser, String refreshToken) {
        if (!findUser.validateRefreshToken(refreshToken))
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
    }

    @Override
    public void updateRefreshToken(String refreshToken, UserEntity userEntity) {
        userEntity.updateRefreshToken(refreshToken);
    }

    @Override
    public boolean findByPlatformAndPlatformId(Platform platform, String platformId) {
        return userRepository.findByPlatformAndPlatformId(platform, platformId) != null;
    }

    @Override
    public Optional<Long> getUserIdByPlatformAndPlatformId(Platform platform, String platformId) {
        return Optional.ofNullable(userRepository.findByPlatformAndPlatformId(platform, platformId))
                .map(UserEntity::getId);
    }

    @Override
    public void checkDuplicatedEmail(String email) {
        if(isAdminMail(email))
            return;
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new GongBaekException(ResponseError.USERNAME_ALREADY_EXISTS);
        }
    }

    private boolean isAdminMail(String email) {
        return email.equals(appProperties.getAndReviewEmail()) || email.equals(appProperties.getIosReviewEmail());
    }

    @Override
    @Transactional
    public void removeRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));

        user.updateRefreshToken(null);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        log.info("delete User start ======");
        userRepository.deleteById(userId);
        log.info("delete User clear ======");
    }
}

