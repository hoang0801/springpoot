package vnpost.technology.entity;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;
import vnpost.technology.utils.PaymentStatus;

import javax.persistence.*;

@Entity
@Data
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Liên kết với đơn hàng (Order)

    private Double amount; // Số tiền thanh toán

    private String method; // Phương thức thanh toán (COD, Online)

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Trạng thái thanh toán
}
