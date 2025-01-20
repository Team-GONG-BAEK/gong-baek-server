package com.ggang.be.api.group.dto;

public record RegisterGroupResponse(Long groupId) {
    public static RegisterGroupResponse of(Long groupId) {
        return new RegisterGroupResponse(groupId);
    }

}
