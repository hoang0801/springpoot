package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnpost.technology.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    @Query("SELECT u FROM Payment u WHERE u.status LIKE :x ")
    Page<Payment> searchByStatus(@Param("x") String s, Pageable pageable);
}
