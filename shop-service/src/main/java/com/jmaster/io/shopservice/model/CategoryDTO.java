package com.jmaster.io.shopservice.model;

import lombok.Data;

import java.util.List;
@Data
public class CategoryDTO {
    private Long  id;

    private String title;

    private String description;

    private String featureImage;

    private List<ProductDTO> products;
}
