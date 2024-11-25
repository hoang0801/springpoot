package vnpost.technology.dto;

import lombok.Data;
import vnpost.technology.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id; // ID của đơn hàng

    private String receiverName; // Tên người nhận

    private String receiverPhone; // Số điện thoại người nhận

    private String receiverAddress; // Địa chỉ người nhận

    private Double weight; // Trọng lượng hàng hóa

    private UserDTO user; // Tên khách hàng (user)

    private String orderCode;

    private ShipperDTO shipper; // Tên shipper (nếu có)

    private OrderStatus status; // Trạng thái đơn hàng

    private List<OrderItemDTO> orderItems;

    private Double codAmount;

    private LocalDateTime updatedAt; // Thời gian cập nhật trạng thái đơn hàng
}
