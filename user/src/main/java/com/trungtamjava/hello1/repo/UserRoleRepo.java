package com.trungtamjava.hello1.repo;

import com.trungtamjava.hello1.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer>{

    void deleteByRole(String role);//xoa theo cot role

    @Modifying
    @Query("DELETE FROM UserRole ur JOIN ur.user u WHERE u.id =:uid")
    public  void deleteByUserId(@Param("uid") int userid);

    @Query("SELECT FROM UserRole ur JOIN ur.user u WHERE u.id =:uid")
    Page<UserRole> searchByUserId(@Param("uid") int userId, Pageable pageable);

    @Query("SELECT FROM UserRole ur JOIN ur.user u WHERE ur.role =role")
    Page<UserRole> searchByRole(@Param("role") String role, Pageable pageable);
}
