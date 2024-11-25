package vnpost.technology.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

        private Integer id;

        private String name;

        private ProducDTO product; // Thông tin sản phẩm (DTO)

        private int quantity; // Số lượng

        private double price; // Giá sản phẩm tại thời điểm đặt hàng
}