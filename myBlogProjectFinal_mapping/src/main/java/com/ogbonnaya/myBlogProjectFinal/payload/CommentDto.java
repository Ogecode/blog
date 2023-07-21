package com.ogbonnaya.myBlogProjectFinal.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class CommentDto {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String body;


}
