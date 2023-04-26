package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.BillDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/bill")
public class BillAPIController {
    @Autowired
    private BillService billService;

    @PostMapping("/")
    public ResponseDTO<BillDTO> create(@RequestBody @Valid BillDTO billDTO) {
        billService.create(billDTO);
        return ResponseDTO.<BillDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(billDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid BillDTO billDTO) {
        billService.update(billDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<BillDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<BillDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(billService.get(id)).build();
    }



    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        billService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        billService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<BillDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return billService.find(searchDTO);
    }
}
