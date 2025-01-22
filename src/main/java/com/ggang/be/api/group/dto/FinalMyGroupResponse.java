package com.ggang.be.api.group.dto;

import java.util.List;

public record FinalMyGroupResponse(
        List<MyGroupResponse> groups
) {
}