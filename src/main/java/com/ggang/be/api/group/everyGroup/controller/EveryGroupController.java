package com.ggang.be.api.group.everyGroup.controller;

import com.ggang.be.domain.everyGroup.EveryGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EveryGroupController {
    private final EveryGroupService everyGroupService;

    public EveryGroupController(EveryGroupService everyGroupService){
        this.everyGroupService = everyGroupService;
    }

}
