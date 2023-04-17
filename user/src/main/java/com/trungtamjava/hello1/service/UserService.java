package com.trungtamjava.hello1.service;

import com.trungtamjava.hello1.dto.PageDTO;
import com.trungtamjava.hello1.dto.UserDTO;
import com.trungtamjava.hello1.dto.UserRoleDTO;
import com.trungtamjava.hello1.entity.User;
import com.trungtamjava.hello1.entity.UserRole;
import com.trungtamjava.hello1.repo.UserRepo;
import com.trungtamjava.hello1.repo.UserRoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Transactional
    public static void create(UserDTO userDTO){
        User user = new ModelMapper().map(userDTO, User.class);
//        //convertdto ->entity
//        user.setName(userDTO.getName());
//        user.setUsername(userDTO.getUsername());
//        user.setBirthdate(userDTO.getBirthdate());
//        user.setPassword(userDTO.getPassword());
//        user.setAvatar(userDTO.getAvatar());

//		  for (UserRole userRole : user.getUserRoles())
//			       userRole.setUser(user);
//        //rollback


        List<UserRoleDTO> userRoleDTOS = userDTO.getUserRoleS();
        for (UserRoleDTO userRoleDTO : userRoleDTOS){
            if (userRoleDTO.getRole() != null){
                //save to db
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(userRoleDTO.getRole());

                userRoloRepo

            }
        }
    }
    @Transactional
    public void delete(int id) { //delete by user  id
        userRepo.deleteById(id);
    }

    public PageDTO<UserDTO> searchByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<User>  pageRS =
                userRepo.searchByName("%" + name + "%", pageable);

        PageDTO<UserDTO> pageDTO= new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setGetTotalElements(pageRS.getTotalElements());

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : pageRS.getContent()){
            userDTOS.add(new ModelMapper().map(user, UserDTO.class));
        }
        pageDTO.setContents(userDTOS);//set vao pagedto
        return  pageDTO;
    }

    public  UserDTO getById( int id){
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);//java8 lambda
//        UserDTO userDTO = new UserDTO();
//        // modelmapper
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setBirthdate(user.getBirthdate());
////        userDTO.setPassword(user.getPassword());
//        userDTO.setAvatar(user.getAvatar());
        UserDTO userDTO = new ModelMapper().map(user,UserDTO.class);//thu vien modelmapper tu set,get
        return userDTO;

    }
    public  void uodate( UserDTO userDTO){
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);//java8 lambda
//        // modelmapper
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setBirthdate(user.getBirthdate());
////      userDTO.setPassword(user.getPassword());
//        userDTO.setAvatar(user.getAvatar());
        UserDTO userDTO = new ModelMapper().map(user,UserDTO.class);//thu vien modelmapper tu set,get
        return userDTO;
}
