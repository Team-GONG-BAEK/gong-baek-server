package com.ggang.be.api.group.everyGroup.controller;

import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EveryGroupController {
    private final EveryGroupService everyGroupService;

}
