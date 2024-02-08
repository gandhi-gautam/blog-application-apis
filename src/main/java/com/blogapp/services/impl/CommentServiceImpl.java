package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = dtoToComment(commentDto);
        comment.setPost(post);
        Comment createdComment = commentRepo.save(comment);
        return commentToDto(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepo.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
        commentRepo.delete(comment);
    }

    private Comment dtoToComment(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    private CommentDto commentToDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }
}
