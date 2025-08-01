package com.ggang.be.domain.school.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.infra.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;

    @Override
    public List<String> searchSchoolContainingKeyword(String searchKeyword) {
        return schoolRepository.findContainingSearchKeyword(searchKeyword)
                .stream()
                .map(SchoolEntity::getSchoolName)
                .toList();
    }

    @Override
    public List<String> searchSchoolContainingKeywordBoth(String searchKeyword) {
        return schoolRepository.findContainingSearchKeywordBoth(searchKeyword)
                .stream()
                .map(SchoolEntity::getSchoolName)
                .distinct()
                .toList();
    }

    @Override
    public School findSchoolByName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName)
                .map(School::fromEntity)
                .orElseThrow(() -> new GongBaekException(ResponseError.NOT_FOUND));
    }

    @Override
    public SchoolEntity findSchoolEntityByName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName)
                .orElseThrow(() -> new GongBaekException(ResponseError.NOT_FOUND));
    }

    @Override
    public String findSchoolDomainByName(String schoolName) {
        return schoolRepository.findDomainBySchoolName(schoolName)
                .orElseThrow(() -> new GongBaekException(ResponseError.NOT_FOUND));
    }
}
