package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.CategoryDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/category")
public class CategoryAPIController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseDTO<CategoryDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return ResponseDTO.<CategoryDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(categoryDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CategoryDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<CategoryDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(categoryService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        categoryService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        categoryService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<CategoryDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return categoryService.find(searchDTO);
    }
}
