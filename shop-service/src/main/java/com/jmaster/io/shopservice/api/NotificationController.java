package com.jmaster.io.shopservice.api;

import com.jmaster.io.shopservice.model.NotificationDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/")
    public ResponseDTO<NotificationDTO> create(@RequestBody @Valid NotificationDTO notificationDTO) {
        notificationService.create(notificationDTO);
        return ResponseDTO.<NotificationDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(notificationDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid NotificationDTO notificationDTO) {
        notificationService.update(notificationDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<NotificationDTO> get(@PathVariable(value = "id") long id) {
        return ResponseDTO.<NotificationDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(notificationService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") long id) {
        notificationService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Long> ids) {
        notificationService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<NotificationDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return notificationService.find(searchDTO);
    }
}
