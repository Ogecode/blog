package com.ogbonnaya.VotingProject5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data
@Table(name = "Members", uniqueConstraints = {@UniqueConstraint(columnNames = {"TelephoneNo"})})
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "TelephoneNo", nullable = false)
    private int telephoneNo;

    @Column(name = "Age", nullable = false)
    private int age;

}
