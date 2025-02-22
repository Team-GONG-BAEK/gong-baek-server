package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.NicknameValidator;
import com.ggang.be.api.user.dto.SignupRequest;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.group.IntroductionValidator;
import com.ggang.be.global.annotation.Facade;
import com.ggang.be.global.util.TimeValidator;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SignupRequestFacade {

    private final UserService userService;

    public void validateSignupRequest(SignupRequest request) {
        userService.duplicateCheckNickname(request.nickname());
        IntroductionValidator.isIntroductionValid(request.introduction());
        NicknameValidator.validate(request.nickname());
        TimeValidator.hasDuplicateInfo(request.timeTable());
        TimeValidator.isTimeVoValidTime(request.timeTable());

        isValidSchoolGrade(request.schoolGrade());
        TimeValidator.isYearAfterNow(request.enterYear());
    }

    private void isValidSchoolGrade(Integer schoolGrade) {
        if (schoolGrade < 1 || schoolGrade > 4) {
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }


}
