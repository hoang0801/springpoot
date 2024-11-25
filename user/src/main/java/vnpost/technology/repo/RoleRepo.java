package vnpost.technology.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vnpost.technology.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query("SELECT r FROM Role r WHERE r.name LIKE :x")
    Page<Role> searchByName(@Param("x") String s, Pageable pageable);
}
