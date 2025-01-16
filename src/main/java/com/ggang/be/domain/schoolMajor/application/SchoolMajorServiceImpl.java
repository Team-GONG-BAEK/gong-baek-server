package com.ggang.be.domain.schoolMajor.application;

import com.ggang.be.api.schoolMajor.service.SchoolMajorService;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import com.ggang.be.domain.schoolMajor.dto.SearchSchoolMajorVo;
import com.ggang.be.domain.schoolMajor.infra.SchoolMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolMajorServiceImpl implements SchoolMajorService {
    private final SchoolMajorRepository schoolMajorRepository;

    @Override
    public List<SearchSchoolMajorVo> findSchoolMajorBySchoolAndMajorName(Long id,
        String schoolMajorKeyword) {
        return schoolMajorRepository.findBySchoolIdAndMajorKeyword(id, schoolMajorKeyword)
            .stream()
            .map(SchoolMajorEntity::getMajorName)
            .map(SearchSchoolMajorVo::of)
            .toList();
    }
}
