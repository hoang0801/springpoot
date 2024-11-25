package vnpost.technology.entity;

import lombok.Data;
import vnpost.technology.utils.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String orderCode; // Mã vận đơn (unique, tối đa 20 ký tự)

    @Column(nullable = false, length = 100)
    private String receiverName; // Tên người nhận (tối đa 100 ký tự)

    @Column(nullable = false, length = 15)
    private String receiverPhone; // Số điện thoại người nhận (tối đa 15 ký tự)

    @Column(nullable = false, length = 255)
    private String receiverAddress; // Địa chỉ người nhận (tối đa 255 ký tự)

    @Column(nullable = false)
    private Double weight; // Trọng lượng hàng hóa (kg)

    @Column(nullable = false)
    private Double codAmount; // Số tiền thu hộ COD (>= 0)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Ngày tạo đơn hàng (không cho phép chỉnh sửa)

    @Column(nullable = true)
    private LocalDateTime updatedAt; // Ngày cập nhật trạng thái đơn hàng (nếu có)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người dùng tạo đơn hàng

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_id") // Shipper phụ trách đơn hàng
    private Shipper shipper;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDING; // Trạng thái đơn hàng mặc định là PENDING

    // Xử lý logic tự động thêm thời gian tạo đơn hàng
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
