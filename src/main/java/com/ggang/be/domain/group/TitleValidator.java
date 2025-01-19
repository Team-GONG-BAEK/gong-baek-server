package com.ggang.be.domain.group;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.global.util.LengthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TitleValidator {


    public void isGroupTitleValid(final String title) {
        log.info("now value is : {}", title);
        if(title.contains("\n")) {
            log.error("그룹 제목에 엔터가 들어갔습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
        if(!LengthValidator.rangelengthCheck(title, 2, 20)) {
            log.error("그룹 제목 길이 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }
}
