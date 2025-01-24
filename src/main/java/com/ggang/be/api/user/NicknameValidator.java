package com.ggang.be.api.user;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.global.util.LengthValidator;

import java.util.regex.Pattern;

public class NicknameValidator {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 8;
    private static final Pattern koreanPattern = Pattern.compile("^[가-힣]+$");

    public static void validate(String nickname){
        if(!LengthValidator.rangelengthCheck(nickname, MIN_LENGTH, MAX_LENGTH))
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        if(!koreanPattern.matcher(nickname).find())
            throw new GongBaekException(ResponseError.BAD_REQUEST);
    }
}
