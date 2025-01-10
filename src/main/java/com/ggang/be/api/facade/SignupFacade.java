package com.ggang.be.api.facade;

import com.ggang.be.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupFacade {

    private final UserService userService;

    public void duplicateCheckNickname(final String nickname){
        userService.duplicateCheckNickname(nickname);
    }



}
