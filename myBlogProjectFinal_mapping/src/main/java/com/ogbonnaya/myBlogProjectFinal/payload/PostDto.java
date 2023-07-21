package com.ogbonnaya.myBlogProjectFinal.payload;

import lombok.Data;

@Data
public class PostDto {


    private Long id;

    private String title;

    private String description;

    private String content;

    private String createdAt;

    private String author;
}
