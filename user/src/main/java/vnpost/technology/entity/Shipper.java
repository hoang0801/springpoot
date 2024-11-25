package vnpost.technology.entity;

import lombok.Data;
import vnpost.technology.utils.ShipperStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@Entity
@Table(name = "shippers")
public class Shipper {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id; // Khóa chính

        @Column(nullable = false, length = 100)
        private String name; // Tên shipper

        @Column(nullable = false, unique = true)
        @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải chứa đúng 10 chữ số.")
        private String phone; // Số điện thoại shipper

        @Column(nullable = false, unique = true, length = 100)
        private String email; // Email của shipper

        @Enumerated(EnumType.STRING)
        private ShipperStatus status = ShipperStatus.AVAILABLE; // Trạng thái (AVAILABLE, BUSY)

        @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Order> orders; // Danh sách đơn hàng được giao
    }
