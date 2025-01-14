package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.FillGroupType;

public record FillGroupFilterRequest(
        String category,
        boolean status
) {
    public FillGroupType getFillGroupCategory() {
        return FillGroupType.of(this.category);
    }
}
