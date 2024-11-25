package vnpost.technology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.*;
import vnpost.technology.service.RoleService;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role/add")
    public ResponseDTO<RoleDTO> create(@RequestBody @Valid RoleDTO roleDTO) throws IOException {
        roleService.create(roleDTO);
        return ResponseDTO.<RoleDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(roleDTO).build();
    }

    @DeleteMapping("/role/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        roleService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    @PostMapping("/role/search")
    public ResponseDTO<List<RoleDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return roleService.find(searchDTO);
    }

    @PutMapping("/role/update")
    public ResponseDTO<Void> update(@Valid @RequestBody RoleDTO roleDTO) {
        roleService.update(roleDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

}
