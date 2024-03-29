package com.blogapp.services;

import com.blogapp.payloads.PageResponse;
import com.blogapp.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createNewPost(PostDto postDto, int userId, int categoryId);

    PostDto updatePost(PostDto postDto, int postId);

    PostDto getPostById(int postId);

    PageResponse getAllPosts(int pageNumber, int pageSize, String fieldName, String sortDir);

    void deletePost(int postId);

    List<PostDto> getPostsByCategory(int categoryId);

    List<PostDto> getPostsByUser(int userId);

    List<PostDto> searchPost(String keyword);
}
