package com.blogapp.services;

import com.blogapp.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createNewPost(PostDto postDto, int userId, int categoryId);

    PostDto updatePost(PostDto postDto, int postId);

    PostDto getPostById(int postId);

    List<PostDto> getAllPosts();

    void deletePost(int postId);

    List<PostDto> getPostsByCategory(int categoryId);

    List<PostDto> getPostsByUser(int userId);

    List<PostDto> searchPost(String keyword);
}
