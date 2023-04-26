package com.jmaster.io.shopservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String title;

    private String description;

    private String featureImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}
