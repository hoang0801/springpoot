package com.jmaster.io.shopservice.model;


import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {
    private Long id;

    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private String favoriteCategory;

    private Set<RoleDTO> roles ;
}
