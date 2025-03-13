package com.ggang.be.domain.group;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.global.util.LengthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class LocationValidator {

    private static final Pattern pattern = Pattern.compile("^[가-힣a-zA-Z\s0-9]+$");

    public void isLocationValid(final String value){
        log.info("now Location value is : {}", value);
        if(!pattern.matcher(value).matches()) {
            log.error("그룹 장소글 패턴 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
        if (!LengthValidator.rangeLengthCheck(value, 2, 20)) {
            log.error("그룹 장소글 길이 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }
}
