package com.ogbonnaya.myBlogProjectFinal.service;

import com.ogbonnaya.myBlogProjectFinal.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost (PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById (Long id);

    PostDto updatePost (PostDto postDto, Long id);

    void deletePostById (Long id);
}
