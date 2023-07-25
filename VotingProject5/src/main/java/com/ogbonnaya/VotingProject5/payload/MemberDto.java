package com.ogbonnaya.VotingProject5.payload;

import lombok.Data;

@Data
public class MemberDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private int telephoneNo;

    private int age;

}

