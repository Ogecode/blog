package com.ogbonnaya.myBlogProjectFinal.service;

import com.ogbonnaya.myBlogProjectFinal.payload.PostDto;
import com.ogbonnaya.myBlogProjectFinal.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost (PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById (Long id);

    PostDto updatePost (PostDto postDto, Long id);

    void deletePostById (Long id);
}
