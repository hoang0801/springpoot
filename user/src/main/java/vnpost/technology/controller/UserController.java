package vnpost.technology.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.UserDTO;
import vnpost.technology.service.UserService;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/add")
    public ResponseDTO<UserDTO> create(@RequestBody @Valid UserDTO userDTO) throws IOException {
        userService.create(userDTO);
        return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    @PostMapping("/user/search")
    public ResponseDTO<List<UserDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return userService.find(searchDTO);
    }

    @PutMapping("/user/update")
    public ResponseDTO<Void> update(@Valid @RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    }
