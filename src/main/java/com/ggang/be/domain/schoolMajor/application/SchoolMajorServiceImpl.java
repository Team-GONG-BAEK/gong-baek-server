package com.ggang.be.domain.schoolMajor.application;

import com.ggang.be.api.schoolMajor.service.SchoolMajorService;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import com.ggang.be.domain.schoolMajor.infra.SchoolMajorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolMajorServiceImpl implements SchoolMajorService {
    private final SchoolMajorRepository schoolMajorRepository;

    @Override
    public List<String> findSchoolMajorBySchoolAndMajorName(Long id,
        String schoolMajorKeyword) {
        return schoolMajorRepository.findBySchoolIdAndMajorKeyword(id, schoolMajorKeyword)
            .stream()
            .map(SchoolMajorEntity::getMajorName)
            .toList();
    }
}
