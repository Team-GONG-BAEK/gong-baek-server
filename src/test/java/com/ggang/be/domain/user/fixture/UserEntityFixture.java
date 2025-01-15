package com.ggang.be.domain.user.fixture;

import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;

public class UserEntityFixture {


    public static UserEntity createByNickname(String nickname){

        SchoolEntity school = SchoolEntity.builder().schoolName("school").build();

        return UserEntity.builder()
            .nickname(nickname)
            .school(school)
            .schoolGrade(3)
            .gender(Gender.MAN)
            .introduction("introduction")
            .mbti(Mbti.INFJ)
            .profileImg(1)
            .enterYear(2020)
            .schoolMajorName("Computer Science")
            .build();
    }

    public static UserEntity createBySchool(SchoolEntity school){
        return UserEntity.builder()
            .nickname("test")
            .school(school)
            .schoolGrade(3)
            .gender(Gender.MAN)
            .introduction("introduction")
            .mbti(Mbti.INFJ)
            .profileImg(1)
            .enterYear(2020)
            .schoolMajorName("Computer Science")
            .build();
    }


    public static UserEntity create(){

        SchoolEntity school = SchoolEntity.builder().schoolName("school").build();

        return UserEntity.builder()
            .nickname("nickname")
            .school(school)
            .schoolGrade(3)
            .gender(Gender.MAN)
            .introduction("introduction")
            .mbti(Mbti.INFJ)
            .profileImg(1)
            .enterYear(2020)
            .schoolMajorName("Computer Science")
            .build();
    }


}
