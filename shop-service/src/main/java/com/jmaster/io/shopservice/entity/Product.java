package com.jmaster.io.shopservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String title;

    private String description;

    private String type;

    private String featureImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
