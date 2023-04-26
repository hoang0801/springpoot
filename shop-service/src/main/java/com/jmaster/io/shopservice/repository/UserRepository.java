package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT n FROM User n WHERE n.favoriteCategory LIKE :favoriteCategory ")
    Page<User> find(@Param("favoriteCategory") String value, Pageable pageable);
}
