package vnpost.technology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.ShipperDTO;
import vnpost.technology.dto.UserDTO;
import vnpost.technology.service.ShipperService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping("/shipper/add")
    public ResponseDTO<ShipperDTO> create(@RequestBody @Valid ShipperDTO shipperDTO) throws IOException {
        shipperService.create(shipperDTO);
        return ResponseDTO.<ShipperDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(shipperDTO).build();
    }

    @DeleteMapping("/shipper/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        shipperService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    @PostMapping("/shipper/search")
    public ResponseDTO<List<ShipperDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return shipperService.find(searchDTO);
    }

    @PutMapping("/shipper/update")
    public ResponseDTO<Void> update(@Valid @RequestBody ShipperDTO shipperDTO) {
        shipperService.update(shipperDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
}
