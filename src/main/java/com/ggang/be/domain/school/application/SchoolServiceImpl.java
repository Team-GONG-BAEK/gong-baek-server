package com.ggang.be.domain.school.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.dto.SchoolSearchVo;
import com.ggang.be.domain.school.infra.SchoolRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public List<SchoolSearchVo> searchSchoolContainingKeyword(String searchKeyword) {
        return schoolRepository.findContainingSearchKeyword(searchKeyword)
            .stream()
            .map(SchoolEntity::getSchoolName)
            .map(SchoolSearchVo::of)
            .toList();
    }

    @Override
    public School findSchoolByName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName)
            .map(School::fromEntity)
            .orElseThrow(() -> new GongBaekException(ResponseError.NOT_FOUND));
    }
}
