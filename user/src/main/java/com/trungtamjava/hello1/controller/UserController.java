package com.trungtamjava.hello1.controller;

import com.trungtamjava.hello1.dto.PageDTO;
import com.trungtamjava.hello1.dto.UserDTO;
import com.trungtamjava.hello1.entity.User;
import com.trungtamjava.hello1.repo.UserRepo;
import com.trungtamjava.hello1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/new")
    public String add() {
        return "user/add.html";
    }

    @PostMapping("/upload-multi")
    public String add(@RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
        System.out.println(files.length);
        for (MultipartFile file : files)
            if (!file.isEmpty()) {
                final String UPLOAD_FOLDER = "D:/file/";

                String filename = file.getOriginalFilename();
                File newFile = new File(UPLOAD_FOLDER + filename);

                file.transferTo(newFile);
            }
        return "redirect:user/search";
    }


    @PostMapping("/new")
    public String add(@ModelAttribute UserDTO u) throws IllegalStateException, IOException {

        if (u.getFile() != null && !u.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "D:/file/";

            String filename = u.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            u.getFile().transferTo(newFile);

            u.setAvatar(filename);//save to db
        }
        //goi qua service
        UserService.create(u);
        return "redirect:user/search";
    }

    /// /user/download?filename=abc.jpg
    @GetMapping("/download")
    public void Download(@RequestParam("filename") String filename,
                         HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "D:/fi;e/";
        File file = new File(UPLOAD_FOLDER + filename);

        // java.nio.file.Files
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @GetMapping("/search")//
    public String search(@RequestParam(name = "id", required = false) Integer id,
                         @RequestParam(name = "name", required = false) String name,

                         @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                         @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

                         @RequestParam(name = "size", required = false) Integer size,
                         @RequestParam(name = "page", required = false) Integer page, Model model) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

                PageDTO<UserDTO> pageRS =
                        userService.searchByName("%" + name + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getGetTotalElements());
        model.addAttribute("userList", pageRS.getContents());


        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }

    @GetMapping("/get/{id}") //get/10
    public String get(@PathVariable("id")int id, Model model){
        model.addAttribute("user",userService.getById(id));
        return "user/detail.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/edit.html";
    }


}








