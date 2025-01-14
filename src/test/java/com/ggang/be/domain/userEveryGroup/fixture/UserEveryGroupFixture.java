package com.ggang.be.domain.userEveryGroup.fixture;

import com.ggang.be.domain.group.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;

public class UserEveryGroupFixture {


    public static UserEveryGroupEntity create(){
        return UserEveryGroupEntity.builder()
            .userEntity(UserEntityFixture.create())
            .everyGroupEntity(EveryGroupFixture.getTestEveryGroup())
            .build();
    }

    public static UserEveryGroupEntity createByNickname(String participantNickname,String creatorByNickname){
        return UserEveryGroupEntity.builder()
            .userEntity(UserEntityFixture.createByNickname(participantNickname))
            .everyGroupEntity(EveryGroupFixture.createByUserNickname(creatorByNickname))
            .build();
    }

}
