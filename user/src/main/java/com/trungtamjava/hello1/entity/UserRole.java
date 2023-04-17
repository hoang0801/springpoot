package com.trungtamjava.hello1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //may userrole - to- one user
    @JoinColumn(name="user_id")
    private User user;

    private String role;// ADMIN,MEMBER

    private String createDate;
}
