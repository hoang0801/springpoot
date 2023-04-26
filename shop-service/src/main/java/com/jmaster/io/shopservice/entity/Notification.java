package com.jmaster.io.shopservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "notification")
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private String description;
    private String code;
    @OneToOne
    private User user;
}
