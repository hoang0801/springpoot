package vnpost.technology.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.repo.UserRepo;
import vnpost.technology.dto.UserDTO;
import vnpost.technology.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface UserService {
    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Integer id);

    ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO);



}
@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;


    @Override
    @Transactional
    public void create(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
        userDTO.setId(user.getId()); // trả về ID sau khi tạo
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBirthdate(userDTO.getBirthdate());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepo.deleteById(id);
    }
    @Override
//    @Cacheable(cacheNames = "user", key = "#id")
    public ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<User> page = userRepo.searchByName(searchDTO.getValue(), pageable);

        @SuppressWarnings("unchecked")
        ResponseDTO<List<UserDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(user -> convert(user)).collect(Collectors.toList()));
        return responseDTO;
    }

    private UserDTO convert(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }

}