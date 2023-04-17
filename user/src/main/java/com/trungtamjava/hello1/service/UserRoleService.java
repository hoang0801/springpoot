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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleService {
    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    UserRepo userRepo;

    @Transactional // roll back
    public void create(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserid()).orElseThrow(NoResultException::new);
        userRole.setUser(user);
    }

    @Transactional
    public void update(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepo.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserid()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void delete(int id) { //delete by user role id
        userRoleRepo.deleteById(id);
    }

        @Transactional
    public void deleteall(List<Integer> ids) { //delete nhieu by user role id
        userRoleRepo.deleteAllById(ids);
  }
    @Transactional
    public void deleteByUserId(int userid) { //delete by user role id
        userRoleRepo.deleteByUserId(userid);
    }

    public UserRoleDTO getById(int id) {
        UserRole userRole = userRoleRepo.findById(id).
                orElseThrow(NoResultException::new);
    }
    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId, pageable);

        PageDTO<UserRoleDTO> pageDTO= new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setGetTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
        for (UserRole userRole : pageRS.getContent()){
            userRoleDTOS.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }
        pageDTO.setContents(userRoleDTOS);//set vao pagedto
        return  pageDTO;
    }

}


