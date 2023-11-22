package com.example.springredditclone.controller;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return status(HttpStatus.OK)
            .body(commentService.getCommentByPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName) {
        return status(HttpStatus.OK).body(commentService.getCommentsByUser(userName));
    }

}
