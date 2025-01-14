package com.ggang.be.domain.constant;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;

public enum FillGroupType {
    REGISTER,
    APPLY;

    public static FillGroupType of(String type) {
        return FillGroupType.valueOf(type.toUpperCase());
    }
    public static boolean isValidCategory(FillGroupType fillGroupType) {
        if (fillGroupType == FillGroupType.REGISTER || fillGroupType == FillGroupType.APPLY) {
            return true;
        } else {
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }
}
