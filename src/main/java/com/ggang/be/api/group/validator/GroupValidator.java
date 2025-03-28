package com.ggang.be.api.group.validator;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupValidator {
    public void isValidEveryGroup(EveryGroupEntity everyGroupEntity){
        if(everyGroupEntity.getStatus() == Status.CLOSED){
            throw new GongBaekException(ResponseError.GROUP_ALREADY_CLOSED);
        }
    }

    public void isValidOnceGroup(OnceGroupEntity onceGroupEntity){
        if(onceGroupEntity.getStatus() == Status.CLOSED){
            throw new GongBaekException(ResponseError.GROUP_ALREADY_CLOSED);
        }
    }
}
