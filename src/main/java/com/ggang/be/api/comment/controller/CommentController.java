package com.ggang.be.api.comment.controller;

import com.ggang.be.api.comment.dto.*;
import com.ggang.be.api.comment.facade.CommentFacade;
import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {
    private final CommentFacade commentFacade;
    private final JwtService jwtService;

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<WriteCommentResponse>> writeComment(
            @RequestHeader("Authorization") final String token,
            @RequestBody @Valid final WriteCommentRequest dto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        return ResponseBuilder.created(commentFacade.writeComment(userId, dto));
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<ReadCommentResponse>> getComments(
            @RequestHeader("Authorization") final String token,
            @RequestParam boolean isPublic,
            @RequestParam("groupId") long groupId,
            @RequestParam("groupType") GroupType groupType
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        ReadCommentRequest dto = new ReadCommentRequest(groupId, groupType);

        return ResponseBuilder.ok(commentFacade.readComment(userId, isPublic, dto));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader("Authorization") final String token,
            @RequestBody @Valid final DeleteCommentRequest dto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        commentFacade.deleteComment(userId, dto.commentId());

        return ResponseBuilder.ok(null);
    }
}
