package vnpost.technology.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="route")
@EntityListeners(AuditingEntityListener.class)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false) // Foreign key liên kết đến bảng Order
    private Order order;

    // Chi tiết tuyến đường (VD: Hà Nội -> Bắc Ninh -> Hải Phòng)
    @Column(length = 500)
    private String routeDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    // Thời gian tạo tuyến đường
}
