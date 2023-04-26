package com.jmaster.io.shopservice.model;


import lombok.Data;

@Data
public class ProductDTO {
    private Long  id;

    private String title;

    private String description;

    private String type;

    private String featureImage;

    private CategoryDTO category;
}
