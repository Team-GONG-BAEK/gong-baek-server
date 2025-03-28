package com.ggang.be.domain.common;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SameSchoolValidator {
    public void isUserReadMySchoolEveryGroup(UserEntity userEntity, EveryGroupEntity everyGroupEntity) {
        if(!isEveryGroupHostExist(everyGroupEntity)) return;
        String groupCreatorSchoolName = everyGroupEntity.getUserEntity().getSchool().getSchoolName();
        String userSchoolName = userEntity.getSchool().getSchoolName();
        if (!groupCreatorSchoolName.equals(userSchoolName))
            throw new GongBaekException(ResponseError.GROUP_ACCESS_SCHOOL_MISMATCH);
    }

    public void isUserReadMySchoolOnceGroup(UserEntity userEntity, OnceGroupEntity onceGroupEntity) {
        if(!isOnceGroupHostExist(onceGroupEntity)) return;
        String groupCreatorSchoolName = onceGroupEntity.getUserEntity().getSchool().getSchoolName();
        if(groupCreatorSchoolName == null) return;

        String userSchoolName = userEntity.getSchool().getSchoolName();
        if (!groupCreatorSchoolName.equals(userSchoolName))
            throw new GongBaekException(ResponseError.GROUP_ACCESS_SCHOOL_MISMATCH);

    }

    private boolean isEveryGroupHostExist(EveryGroupEntity everyGroupEntity) {
        return (everyGroupEntity.getUserEntity() != null);
    }

    private boolean isOnceGroupHostExist(OnceGroupEntity onceGroupEntity) {
        return (onceGroupEntity.getUserEntity() != null);
    }

}
