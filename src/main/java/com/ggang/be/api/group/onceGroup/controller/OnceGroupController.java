package com.ggang.be.api.group.onceGroup.controller;

import com.ggang.be.domain.onceGroup.OnceGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OnceGroupController {
    private OnceGroupService onceGroupService;

    public OnceGroupController(OnceGroupService onceGroupService) {
        this.onceGroupService = onceGroupService;
    }
}

