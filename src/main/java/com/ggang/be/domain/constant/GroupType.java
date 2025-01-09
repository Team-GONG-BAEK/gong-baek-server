package com.ggang.be.domain.constant;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;

public enum GroupType {
    WEEKLY,
    ONCE;

    public static boolean isValid(String type) {
        for (GroupType groupType : GroupType.values()) {
            if (groupType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        throw new GongBaekException(ResponseError.BAD_REQUEST);
    }

    public static GroupType fromString(String type) {
        isValid(type);
        return GroupType.valueOf(type);
    }
}
