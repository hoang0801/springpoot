package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Category;
import com.jmaster.io.shopservice.entity.User;
import com.jmaster.io.shopservice.model.CategoryDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.model.UserDTO;
import com.jmaster.io.shopservice.repository.CategoryRepository;
import com.jmaster.io.shopservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    UserDTO get(long id);


    ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO);

}

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void create(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        userRepository.save(user);
        userDTO.setId(user.getId());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class)
                .setProvider(p -> userRepository.findById(userDTO.getId()).orElseThrow(NoResultException::new));
        System.out.println(userDTO.toString());
        User user = mapper.map(userDTO, User.class);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        userRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public UserDTO get(long id) {
        return userRepository.findById(id).map(category -> convert(category)).orElseThrow(NoResultException::new);
    }



    @Override
    public ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<User> page = userRepository.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<UserDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(category -> convert(category)).collect(Collectors.toList()));
        return responseDTO;
    }

    private UserDTO convert(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}