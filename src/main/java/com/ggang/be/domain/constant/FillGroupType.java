package com.ggang.be.domain.constant;

public enum FillGroupType {
    REGISTER,
    APPLY;

    public static FillGroupType of(String type) {
        return FillGroupType.valueOf(type.toUpperCase());
    }
    public static boolean isValidCategory(String category) {
        try {
            FillGroupType.valueOf(category);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
