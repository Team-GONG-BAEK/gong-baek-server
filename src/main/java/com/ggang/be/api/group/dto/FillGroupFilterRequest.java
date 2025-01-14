package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.FillGroupType;

public record FillGroupFilterRequest(
        FillGroupType category,
        boolean status
) {
    public FillGroupType getFillGroupCategory() {
        return this.category;
    }
}
