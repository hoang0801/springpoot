package com.jmaster.io.shopservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BillDTO {
    private Long  id;

    private Long price;

    private CartDTO cart;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    private UserDTO createdBy;
}
