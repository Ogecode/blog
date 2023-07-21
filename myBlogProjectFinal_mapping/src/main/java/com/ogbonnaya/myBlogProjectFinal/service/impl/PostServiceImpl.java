package com.ogbonnaya.myBlogProjectFinal.service.impl;

import com.ogbonnaya.myBlogProjectFinal.entity.Post;
import com.ogbonnaya.myBlogProjectFinal.exception.ResourceNotFoundException;
import com.ogbonnaya.myBlogProjectFinal.payload.PostDto;
import com.ogbonnaya.myBlogProjectFinal.payload.PostResponse;
import com.ogbonnaya.myBlogProjectFinal.repository.PostRepository;
import com.ogbonnaya.myBlogProjectFinal.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    //injecting postRepository because we would be using PostRepository MEthods
    private PostRepository postRepository;

        // generate constructor for postRepo
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        /* convert DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setCreatedAt(postDto.getCreatedAt()); */

        //  passing the methos converting DTO into an entity to the save method
        //  then call repository to save DTO in the DB
        Post post = mapToEntity(postDto);
       Post newPost = postRepository.save(post);


       //bringing the converted entity to DTO to the be mapped to post response
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts( int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create a pageable instance

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // getting content from the page object
       List<Post> listOfPosts = posts.getContent();
       //convert list of post to postdto

        List <PostDto> content = listOfPosts.stream().map(post-> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse =  new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return  postResponse;

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        //convert to postdto
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get post by ID from the Db
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        //update using any of these methods
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthor());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    // create a private method that would map all entitty to DTO and you can reuse it
    // converting Entity to DTO in the below method
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setAuthor(post.getAuthor());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setTitle(post.getTitle());

        return postDto;
    }

   // converting DTO into an Entity method for all method to use
    private  Post mapToEntity (PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setCreatedAt(postDto.getCreatedAt());

        return post;
    }

}
