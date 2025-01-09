package com.ggang.be.domain.constant;

public enum GroupType {
    WEEKLY,
    ONCE;

    public static boolean isValid(String type) {
        for (GroupType groupType : GroupType.values()) {
            if (groupType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
