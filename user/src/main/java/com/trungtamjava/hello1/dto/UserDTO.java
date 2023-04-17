package com.trungtamjava.hello1.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank //calidation
    private String name;

    private String avatar; //URl
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private MultipartFile file;

    private Date createdAT;

    private List<UserRoleDTO> userRoleS;
}
