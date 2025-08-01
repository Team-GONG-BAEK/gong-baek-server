package com.ggang.be.domain.schoolMajor.application;

import com.ggang.be.api.schoolMajor.service.SchoolMajorService;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import com.ggang.be.domain.schoolMajor.infra.SchoolMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolMajorServiceImpl implements SchoolMajorService {
    private final SchoolMajorRepository schoolMajorRepository;

    @Override
    public List<String> findSchoolMajorBySchoolAndMajorName(
            Long id,
            String schoolMajorKeyword
    ) {
        return schoolMajorRepository.findBySchoolIdAndMajorKeyword(id, schoolMajorKeyword)
                .stream()
                .map(SchoolMajorEntity::getMajorName)
                .toList();
    }

    @Override
    public List<String> findSchoolMajorBySchoolAndMajorNameBoth(
            Long id,
            String schoolMajorKeyword
    ) {
        if (!StringUtils.hasText(schoolMajorKeyword)) {
            return schoolMajorRepository.findBySchoolId(id)
                    .stream()
                    .map(SchoolMajorEntity::getMajorName)
                    .toList();
        }

        return schoolMajorRepository.findBySchoolIdAndMajorKeywordBoth(id, schoolMajorKeyword)
                .stream()
                .map(SchoolMajorEntity::getMajorName)
                .distinct()
                .toList();
    }
}
