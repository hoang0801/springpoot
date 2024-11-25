package vnpost.technology.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnpost.technology.dto.OrderDTO;
import vnpost.technology.dto.OrderItemDTO;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.entity.*;
import vnpost.technology.repo.OrderItemRepo;
import vnpost.technology.repo.OrderRepo;
import vnpost.technology.repo.ProductRepo;
import vnpost.technology.repo.UserRepo;
import vnpost.technology.utils.OrderStatus;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public interface OrderService {
//    void create(OrderDTO orderDTO,User currentUser);
    void create(OrderDTO orderDTO);

    void update(OrderDTO orderDTO);

    void delete(Integer id);

    ResponseDTO<List<OrderDTO>> searchCode(SearchDTO searchDTO);



}
@Service
class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    private static final String DIGITS = "0123456789"; // Chỉ lấy số
    private static final int CODE_LENGTH = 10; // Độ dài của mã (số)

    private String generateTrackingCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder orderCode = new StringBuilder("VNP");
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(DIGITS.length());
            orderCode.append(DIGITS.charAt(index)); // Chọn ngẫu nhiên chữ số
        }
        return orderCode.toString();
    }

    private String generateUniqueTrackingCode() {
        String orderCode;
        do {
            orderCode = generateTrackingCode();
        } while (orderRepo.existsByOrderCode(orderCode)); // Kiểm tra trùng mã
        return orderCode;
    }



    @Override
    @Transactional
    public void create(OrderDTO orderDTO ) {

        // Tạo ModelMapper và ánh xạ OrderDTO sang Order
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class); // Ánh xạ OrderDTO sang Order

        // Tạo mã vận đơn duy nhất
        String orderCode = generateUniqueTrackingCode(); // Gọi hàm tạo mã vận đơn
        order.setOrderCode(orderCode); // Gán mã vận đơn cho đơn hàng

        // Gán khách hàng từ UserDTO
        if (orderDTO.getUser() != null && orderDTO.getUser().getId() != null) {
            User user = new User(); // Tạo một đối tượng User từ UserDTO
            user.setId(orderDTO.getUser().getId()); // Gán ID người dùng từ DTO vào đối tượng User
            order.setUser(user); // Gán người dùng cho đơn hàng
        } else {
            throw new RuntimeException("User not found in order data.");
        }
        // Gán shipper nếu có
        if (orderDTO.getShipper() != null) {
            Shipper shipper = new Shipper();
            shipper.setId(orderDTO.getShipper().getId()); // Gán ID shipper từ DTO
            order.setShipper(shipper); // Gán shipper cho đơn hàng
        }

        // Duyệt qua các OrderItemDTO và chuyển thành OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(orderItemDTO.getName());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());

            // Liên kết OrderItem với Order
            orderItem.setOrder(order);

            // Thêm OrderItem vào danh sách
            orderItems.add(orderItem);
        }

        // Gán danh sách OrderItems vào Order
        order.setOrderItems(orderItems);

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderRepo.save(order); // Giả sử bạn sử dụng Spring Data JPA hoặc một repository tương tự
        orderItemRepo.saveAll(orderItems);
    }


    @Override
    @Transactional
    public void update(OrderDTO orderDTO) {
        Order order = orderRepo.findById(orderDTO.getId()).orElseThrow(NoResultException::new);
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order.setReceiverAddress(orderDTO.getReceiverAddress());
        order.setWeight(orderDTO.getWeight());
        order.setStatus(orderDTO.getStatus()); // Cập nhật trạng thái đơn hàng nếu có
        order.setCodAmount(orderDTO.getCodAmount()); // Cập nhật số tiền COD nếu có


        // Cập nhật danh sách sản phẩm (orderItems)
        if (orderDTO.getOrderItems() != null) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setName(orderItemDTO.getName());
                orderItem.setQuantity(orderItemDTO.getQuantity());
                orderItem.setPrice(orderItemDTO.getPrice());
                orderItem.setOrder(order); // Gắn order vào mỗi item

                // Thêm vào danh sách orderItems
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems); // Gán lại danh sách orderItems
        }

        orderRepo.save(order);


    }

    @Override
    @Transactional
    public void delete(Integer id) {
        orderRepo.deleteById(id);
    }

    @Override
    public ResponseDTO<List<OrderDTO>> searchCode(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Order> page = orderRepo.searchByorderCode(searchDTO.getValue(), pageable);

        @SuppressWarnings("unchecked")
        ResponseDTO<List<OrderDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(order -> convert(order)).collect(Collectors.toList()));
        return responseDTO;
    }

    private OrderDTO convert(Order order) {
        return new ModelMapper().map(order, OrderDTO.class);
    }

}

