package com.ggang.be.domain.user.dto;

import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.school.SchoolEntity;

public record SaveUserSignUp(Platform platform,
                             String platformUserId,
                             Integer profileImg,
                             String nickname,
                             Mbti mbti,
                             String schoolMajorName,
                             Integer enterYear,
                             String introduction,
                             Gender sex,
                             SchoolEntity school) {
}
