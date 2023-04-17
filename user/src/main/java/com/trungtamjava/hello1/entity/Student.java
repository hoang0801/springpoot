package com.trungtamjava.hello1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
    @Entity
    @Data
    public class Student {
        @Id
        private Integer id;

        @Column(unique = true)
        private String studentCode;

        @OneToOne
        @PrimaryKeyJoinColumn
        private User user;


        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "score", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name="scoure_id"))
        private List<Course> courses;
    }
