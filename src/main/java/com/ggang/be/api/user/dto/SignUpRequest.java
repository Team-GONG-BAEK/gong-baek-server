package com.ggang.be.api.user.dto;

import com.ggang.be.api.user.vo.TimeTableVo;
import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;

import java.util.List;

public record SignUpRequest(Platform platform,
                            String email,
                            Integer profileImg,
                            String nickname,
                            Mbti mbti,
                            String schoolName,
                            String schoolMajor,
                            Integer enterYear,
                            String introduction,
                            Gender sex,
                            List<TimeTableVo> timeTable
) {

    public static SaveUserSignUp toSaveUserSignUp(SignUpRequest request, String platformUserId, SchoolEntity school) {
        return new SaveUserSignUp(
                request.platform(),
                platformUserId,
                request.profileImg(),
                request.email(),
                request.nickname(),
                request.mbti(),
                request.schoolMajor(),
                request.enterYear(),
                request.introduction(),
                request.sex(),
                school
        );
    }
}
