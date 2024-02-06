package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createNewPost(PostDto postDto, int userId, int categoryId) {

        // get user
        User user = userRepo.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User", "User ID", userId));
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = dtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        post = postRepo.save(post);
        PostDto createdPost = postToDto(post);
        return createdPost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        return null;
    }

    @Override
    public PostDto getPostById(int postId) {
        return null;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return null;
    }

    @Override
    public void deletePost(int postId) {

    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        return null;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return null;
    }

    private Post dtoToPost(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private PostDto postToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }
}
