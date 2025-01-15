package com.ggang.be.api.group.onceGroup.controller;

import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OnceGroupController {
    private final OnceGroupService onceGroupService;
}
