package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnpost.technology.entity.Role;
import vnpost.technology.entity.Shipper;

public interface ShipperRepo extends JpaRepository<Shipper,Integer> {
    @Query("SELECT s FROM Shipper s WHERE s.name LIKE :x")
    Page<Shipper> searchByName(@Param("x") String s, Pageable pageable);
}
