package com.ggang.be.api.comment.controller;

import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.facade.CommentFacade;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;
    private final JwtService jwtService;

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<WriteCommentResponse>> writeComment(@RequestHeader("Authorization") final String token, @RequestBody final WriteCommentRequest dto){
        String parsedToken = jwtService.parseTokenAndGetUserId(token);
        long userId = Long.parseLong(parsedToken);
        return ResponseBuilder.created(commentFacade.writeComment(userId, dto));
    }

}
