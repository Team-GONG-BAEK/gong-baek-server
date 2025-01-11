package com.ggang.be.api.user.service;

import com.ggang.be.domain.user.dto.UserSchoolDto;

public interface UserService {
    UserSchoolDto getUserSchoolById(Long userId);
  
    boolean duplicateCheckNickname(String nickname);

}
