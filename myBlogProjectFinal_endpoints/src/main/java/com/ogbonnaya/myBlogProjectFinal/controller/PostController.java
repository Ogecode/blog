package com.ogbonnaya.myBlogProjectFinal.controller;


import com.ogbonnaya.myBlogProjectFinal.payload.PostDto;
import com.ogbonnaya.myBlogProjectFinal.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blogPost
    @PostMapping
    public ResponseEntity<PostDto> createPost (@RequestBody PostDto postDto){
       return  new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //getall post API
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }


    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by id
    @PutMapping("/{id}")
    public  ResponseEntity<PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable (name = "id") long id){

       PostDto postResponse = postService.updatePost(postDto, id);
       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //delete post by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (name = "id") long id) {
        postService.deletePostById(id);
        return  new ResponseEntity<>("Post Entity has been successfully deleted", HttpStatus.OK);
    }
}
