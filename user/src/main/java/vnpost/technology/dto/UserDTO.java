package vnpost.technology.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    private Integer id;

    private String name;

    private String  phoneNumber;

    private String username;

    private String password;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthdate;

    private List<RoleDTO> roles;
}