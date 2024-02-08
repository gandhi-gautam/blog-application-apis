package com.blogapp.services;

import com.blogapp.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, int postId);
    void deleteComment(int commentId);
}
