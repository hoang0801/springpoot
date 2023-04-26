package com.jmaster.io.shopservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CartDTO {
    private Long id;

    private String title;

    private String description;

    private String featureImage;

    private List<ProductDTO> products;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    private UserDTO createdBy;
}
