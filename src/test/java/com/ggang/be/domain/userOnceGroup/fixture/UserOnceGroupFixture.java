package com.ggang.be.domain.userOnceGroup.fixture;

import com.ggang.be.domain.group.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;

public class UserOnceGroupFixture {

    public static UserOnceGroupEntity create(){
        return UserOnceGroupEntity.builder()
            .userEntity(UserEntityFixture.create())
            .onceGroupEntity(OnceGroupFixture.getTestOnceGroupEntity())
            .build();
    }

    public static UserOnceGroupEntity createByNickname(String participantNickname,String creatorByNickname){
        return UserOnceGroupEntity.builder()
            .userEntity(UserEntityFixture.createByNickname(participantNickname))
            .onceGroupEntity(OnceGroupFixture.createByUserNickname(creatorByNickname))
            .build();
    }

}
