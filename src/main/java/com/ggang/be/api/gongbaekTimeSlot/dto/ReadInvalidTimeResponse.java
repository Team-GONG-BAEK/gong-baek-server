package com.ggang.be.api.gongbaekTimeSlot.dto;

import com.ggang.be.domain.timslot.vo.ReadCommonInvalidTimeVo;
import java.util.List;

public record ReadInvalidTimeResponse(List<ReadCommonInvalidTimeVo> timeTable) {

    public static ReadInvalidTimeResponse fromVo(List<ReadCommonInvalidTimeVo> timeTable) {
        return new ReadInvalidTimeResponse(timeTable);
    }
}
