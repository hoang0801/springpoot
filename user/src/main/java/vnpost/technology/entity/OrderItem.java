package vnpost.technology.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // Liên kết với đơn hàng

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Liên kết với sản phẩm

    @Column(nullable = false)
    private int quantity; // Số lượng

    @Column(nullable = false)
    private double price; // Giá sản phẩm tại thời điểm đặt hàng


}