package com.ggang.be.domain.school.dto;

public record SchoolSearchVo(String schoolName) {
    public static SchoolSearchVo of(String schoolName) {
        return new SchoolSearchVo(schoolName);
    }

}
