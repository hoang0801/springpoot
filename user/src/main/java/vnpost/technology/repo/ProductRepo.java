package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vnpost.technology.entity.Product;
import vnpost.technology.entity.Role;


@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT r FROM Product r WHERE r.name LIKE :name")
    Page<Role> searchByName(@Param("name") String s, Pageable pageable);
}