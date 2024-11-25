package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnpost.technology.entity.Order;
import vnpost.technology.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
    @Query("SELECT u FROM OrderItem u WHERE u.quantity LIKE :x ")
    Page<OrderItem> searchByQuantity(@Param("x") String s, Pageable pageable);
}
