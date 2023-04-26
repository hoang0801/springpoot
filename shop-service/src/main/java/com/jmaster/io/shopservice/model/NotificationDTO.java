package com.jmaster.io.shopservice.model;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long  id;
    private String description;
    private String code;

    private UserDTO user;
}
