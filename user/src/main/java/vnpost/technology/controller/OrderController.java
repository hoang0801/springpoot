package vnpost.technology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.OrderDTO;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.service.OrderService;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public ResponseDTO<OrderDTO> create(@RequestBody @Valid OrderDTO orderDTO) throws IOException {
        orderService.create(orderDTO);
        return ResponseDTO.<OrderDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(orderDTO).build();
    }

    @DeleteMapping("/order/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        orderService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    @PostMapping("/order/searchOrderCode")
    public ResponseDTO<List<OrderDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return orderService.searchCode(searchDTO);
    }

    @PutMapping("/order/update")
    public ResponseDTO<Void> update(@Valid @RequestBody OrderDTO orderDTO) {
        orderService.update(orderDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
}
