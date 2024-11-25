package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnpost.technology.entity.Order;
import vnpost.technology.entity.User;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN o.orderItems WHERE o.orderCode LIKE :x")
    Page<Order> searchByorderCode(@Param("x") String s, Pageable pageable);

    boolean existsByOrderCode(String orderCode);

}
