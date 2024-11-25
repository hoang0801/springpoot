package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vnpost.technology.entity.Route;
import vnpost.technology.entity.Shipper;

public interface RouteRepo extends JpaRepository<Route, Integer> {
    @Query("SELECT s FROM Route s WHERE s.routeDetails LIKE :x")
    Page<Route> searchByRoute(@Param("x") String s, Pageable pageable);
}
