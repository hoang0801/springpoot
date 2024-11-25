package vnpost.technology.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.RoleDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.UserDTO;
import vnpost.technology.entity.Role;
import vnpost.technology.entity.User;
import vnpost.technology.repo.RoleRepo;
import vnpost.technology.repo.UserRepo;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface RoleService {

    void create(RoleDTO roleDTO);

    void update(RoleDTO roleDTO);

    void delete(Integer id);

    ResponseDTO<List<RoleDTO>> find(SearchDTO searchDTO);
}
@Service
class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepo roleRepo;


    @Override
    @Transactional
    public void create(RoleDTO roleDTO) {
        ModelMapper mapper = new ModelMapper();
        Role role = mapper.map(roleDTO, Role.class);
        roleRepo.save(role);
        roleDTO.setId(role.getId()); // trả về ID sau khi tạo
    }

    @Override
    @Transactional
    public void update(RoleDTO roleDTO) {
        Role role = roleRepo.findById(roleDTO.getId()).orElseThrow(NoResultException::new);
        role.setName(roleDTO.getName());
        roleRepo.save(role);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        roleRepo.deleteById(id);
    }


    @Override
    public ResponseDTO<List<RoleDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Role> page = roleRepo.searchByName(searchDTO.getValue(), pageable);

        @SuppressWarnings("unchecked")
        ResponseDTO<List<RoleDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(role -> convert(role)).collect(Collectors.toList()));
        return responseDTO;
    }
    private RoleDTO convert(Role role) {
        return new ModelMapper().map(role, RoleDTO.class);
    }

}