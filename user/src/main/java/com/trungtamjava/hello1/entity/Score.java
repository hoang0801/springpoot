package com.trungtamjava.hello1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Score {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private double score;// diem thi monhoc/nguoi

        @ManyToOne
        private Student student;

        @ManyToOne
        private Course course;
    }

