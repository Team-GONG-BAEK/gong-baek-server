package com.ggang.be.api.user.service;

import com.ggang.be.api.user.dto.UserSchoolResponseDto;

public interface UserService {
    UserSchoolResponseDto getUserSchoolById(Long userId);
  
    boolean duplicateCheckNickname(String nickname);

}
