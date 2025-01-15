package com.ggang.be.api.user.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ggang.be.global.jwt.TokenVo;

public record ReissueResponse(@JsonUnwrapped TokenVo tokenVo) {

}
