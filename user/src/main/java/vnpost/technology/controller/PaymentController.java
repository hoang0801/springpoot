package vnpost.technology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vnpost.technology.dto.PaymentDTO;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.service.PaymentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/add")
    public ResponseDTO<PaymentDTO> create(@RequestBody @Valid PaymentDTO paymentDTO) throws IOException {
        paymentService.create(paymentDTO);
        return ResponseDTO.<PaymentDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(paymentDTO).build();
    }

    @DeleteMapping("/payment/delete/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        paymentService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
    @PostMapping("/payment/search")
    public ResponseDTO<List<PaymentDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return paymentService.find(searchDTO);
    }

//    @PutMapping("/payment/update")
//    public ResponseDTO<Void> update(@Valid @RequestBody ShipperDTO shipperDTO) {
//        paymentService.update(shipperDTO);
//        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
//    }
}
