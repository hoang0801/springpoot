package vnpost.technology.dto;

import lombok.Data;

@Data
public class ProducDTO {

    private Integer id;  // Đảm bảo có id cho sản phẩm

    private String name;  // Đổi tên trường nameProduct thành name để khớp với entity

    private String description;  // Thêm description vào DTO

    private Double price;  // Đảm bảo có giá cho sản phẩm

    private Long quantity;  // Số lượng sản phẩm trong OrderItemDTO có thể được lưu trữ ở đây
}
