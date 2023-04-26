package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.ProductDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductAPIController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseDTO<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        productService.create(productDTO);
        return ResponseDTO.<ProductDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(productDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid ProductDTO productDTO) {
        productService.update(productDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<ProductDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<ProductDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(productService.get(id)).build();
    }


    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        productService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        productService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<ProductDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return productService.find(searchDTO);
    }
}
