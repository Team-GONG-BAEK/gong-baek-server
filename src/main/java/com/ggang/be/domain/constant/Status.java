package com.ggang.be.domain.constant;

public enum Status {
    RECRUITING,
    RECRUITED,
    CLOSED;

    public boolean isActive() {
        return this == RECRUITING || this == RECRUITED;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }
}
