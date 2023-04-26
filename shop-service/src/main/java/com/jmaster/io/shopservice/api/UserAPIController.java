package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.model.UserDTO;
import com.jmaster.io.shopservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserAPIController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseDTO<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        userService.create(userDTO);
        return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(userService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        userService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<UserDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return userService.find(searchDTO);
    }
}
