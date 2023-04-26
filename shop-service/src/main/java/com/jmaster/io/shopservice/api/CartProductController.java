package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.CartDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/cart")
public class CartProductController {
    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public ResponseDTO<CartDTO> create(@RequestBody @Valid CartDTO cartDTO) {
        cartService.create(cartDTO);
        return ResponseDTO.<CartDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(cartDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid CartDTO cartDTO) {
        cartService.update(cartDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CartDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<CartDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(cartService.get(id)).build();
    }



    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        cartService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        cartService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<CartDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return cartService.find(searchDTO);
    }
}