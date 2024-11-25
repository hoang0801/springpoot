package vnpost.technology.dto;

import lombok.Data;
import vnpost.technology.entity.Order;
import vnpost.technology.utils.PaymentStatus;

@Data
public class PaymentDTO {

    private Integer id;

    private Order order;

    private Double amount; // Số tiền thanh toán

    private String method; // Phương thức thanh toán (COD, Online)

    private PaymentStatus status; // Trạng thái thanh toán
}
