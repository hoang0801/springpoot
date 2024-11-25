package vnpost.technology.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteDTO {

    private Integer id;

    private Integer orderId;  // Chỉ lấy ID của order liên kết

    private String routeDetails;

    private LocalDateTime createdAt;
}
