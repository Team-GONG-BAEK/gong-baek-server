package com.ggang.be.domain.constant;

public enum Status {
    RECRUITING,
    RECRUITED,
    CLOSED;

    public boolean isActive() {
        return this == RECRUITING;
    }
}
