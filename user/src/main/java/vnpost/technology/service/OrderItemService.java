package vnpost.technology.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import vnpost.technology.dto.*;
import vnpost.technology.entity.OrderItem;
import vnpost.technology.entity.Shipper;
import vnpost.technology.repo.OrderItemRepo;
import vnpost.technology.repo.ShipperRepo;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface OrderItemService {

    void create(OrderItemDTO orderItemDTO);

    void delete(Integer id);
//
//    ResponseDTO<List<OrderItemDTO>> find(SearchDTO searchDTO);
}
class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepo orderItemRepo;


    @Override
    @Transactional
    public void create(OrderItemDTO orderItemDTO) {
        ModelMapper mapper = new ModelMapper();
        OrderItem orderItem = mapper.map(orderItemDTO, OrderItem.class);
        orderItemDTO.setId(orderItem.getId());
        orderItemRepo.save(orderItem);

    }

    @Override
    @Transactional
    public void delete(Integer id) {

        orderItemRepo.deleteById(id);
    }
//
//    @Override
//    public ResponseDTO<List<ShipperDTO>> find(SearchDTO searchDTO) {
//        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
//                .map(order -> {
//                    if (order.getOrder().equals(SearchDTO.ASC))
//                        return Sort.Order.asc(order.getProperty());
//
//                    return Sort.Order.desc(order.getProperty());
//                }).collect(Collectors.toList());
//
//        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));
//
//        Page<Shipper> page = shipperRepo.searchByName(searchDTO.getValue(), pageable);
//
//        @SuppressWarnings("unchecked")
//        ResponseDTO<List<ShipperDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
//        responseDTO.setData(page.get().map(shipper -> convert(shipper)).collect(Collectors.toList()));
//        return responseDTO;
//    }
//    private ShipperDTO convert(Shipper shipper) {
//        return new ModelMapper().map(shipper, ShipperDTO.class);
//    }
//}

}
