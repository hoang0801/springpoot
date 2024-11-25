package vnpost.technology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.RouteDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.ShipperDTO;
import vnpost.technology.service.RouteService;
import vnpost.technology.service.ShipperService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/route/add")
    public ResponseDTO<RouteDTO> create(@RequestBody @Valid RouteDTO routeDTO) throws IOException {
        routeService.create(routeDTO);
        return ResponseDTO.<RouteDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(routeDTO).build();
    }

    @DeleteMapping("/route/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        routeService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/route/search")
    public ResponseDTO<List<RouteDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return routeService.find(searchDTO);
    }

    @PutMapping("/route/update")
    public ResponseDTO<Void> update(@Valid @RequestBody RouteDTO routeDTO) {
        routeService.update(routeDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
}
