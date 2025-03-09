package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.user.dto.SignUpRequest;
import com.ggang.be.api.user.dto.SignUpResponse;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.user.vo.TimeTableVo;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignUpFacade {

    private final UserService userService;
    private final SchoolService schoolService;
    private final LectureTimeSlotService lectureTimeSlotService;
    private final JwtService jwtService;

    public void duplicateCheckNickname(final String nickname) {
        userService.duplicateCheckNickname(nickname);
    }

    @Transactional
    public SignUpResponse signUp(String platformId, SignUpRequest request) {
        SchoolEntity schoolEntityByName = schoolService.findSchoolEntityByName(request.schoolName());

        SaveUserSignUp saveUserSignUp = SignUpRequest.toSaveUserSignUp(request, platformId, schoolEntityByName);

        UserEntity userEntity = userService.saveUserBySignup(saveUserSignUp);

        log.info("SignUp - userEntity: {}", userEntity);
        List<LectureTimeSlotVo> lectureTimeSlotVos = request.timeTable().stream()
                .map(TimeTableVo::toLectureTimeSlotVo)
                .toList();

        lectureTimeSlotService.saveLectureTimeSlot(lectureTimeSlotVos, userEntity);

        return madeSignupResponse(userEntity);
    }

    private SignUpResponse madeSignupResponse(final UserEntity userEntity) {
        Long userId = userEntity.getId();

        log.info("회원가입 완료 - userId: {}", userId);

        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);

        userService.updateRefreshToken(refreshToken, userEntity);

        return SignUpResponse.of(userId, accessToken, refreshToken);
    }
}
