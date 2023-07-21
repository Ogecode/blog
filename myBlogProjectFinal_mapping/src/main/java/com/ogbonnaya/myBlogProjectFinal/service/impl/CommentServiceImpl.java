package com.ogbonnaya.myBlogProjectFinal.service.impl;

import com.ogbonnaya.myBlogProjectFinal.entity.Comment;
import com.ogbonnaya.myBlogProjectFinal.entity.Post;
import com.ogbonnaya.myBlogProjectFinal.exception.BlogApiException;
import com.ogbonnaya.myBlogProjectFinal.exception.ResourceNotFoundException;
import com.ogbonnaya.myBlogProjectFinal.payload.CommentDto;
import com.ogbonnaya.myBlogProjectFinal.repository.CommentRepository;
import com.ogbonnaya.myBlogProjectFinal.repository.PostRepository;
import com.ogbonnaya.myBlogProjectFinal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieving post entity by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity in the DB
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comment by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert list of comment entities to list of comment dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }


    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieving post entity by ID
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post","id", postId));

        //retrieve comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post","id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setFirstName(commentRequest.getFirstName());
        comment.setLastName(commentRequest.getLastName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        }
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setFirstName(comment.getFirstName());
        commentDto.setLastName(comment.getLastName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private  Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setFirstName(commentDto.getFirstName());
        comment.setLastName(commentDto.getLastName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        return comment;
    }
}
