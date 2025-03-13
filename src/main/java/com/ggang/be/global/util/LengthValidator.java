package com.ggang.be.global.util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LengthValidator {
    private final static Pattern emojiPattern = Pattern.compile("\\X");

    public static boolean rangeLengthCheck(final String value, final int minLength, final int maxLength){
        Matcher matcher = emojiPattern.matcher(value);
        long count = matcher.results().count();
        log.info("now value is : {}", value);
        log.info("now value count is : {}", count);
        return count >= minLength && count <= maxLength;
    }

    public static boolean isLongerThanMinLength(final String value, final int minLength){
        Matcher matcher = emojiPattern.matcher(value);
        long count = matcher.results().count();
        log.info("now value is : {}", value);
        log.info("now value count is : {}", count);
        return count >= minLength;
    }

    public static boolean isShorterThanMaxLength(final String value, final int maxLength){
        Matcher matcher = emojiPattern.matcher(value);
        long count = matcher.results().count();
        log.info("now value is : {}", value);
        log.info("now value count is : {}", count);
        return count <= maxLength;
    }
}
