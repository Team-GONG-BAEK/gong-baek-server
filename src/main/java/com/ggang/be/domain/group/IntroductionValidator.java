package com.ggang.be.domain.group;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.global.util.LengthValidator;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IntroductionValidator {


    public void isIntroductionValid(String introduction) {
        log.info("now value is : {}", introduction);
        if(!LengthValidator.rangelengthCheck(introduction, 20, 100)) {
            log.error("소개글 길이 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }
}
