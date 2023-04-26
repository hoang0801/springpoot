package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c JOIN c.createdBy cr WHERE cr.id = :uid ")
    Page<Cart> find(@Param("uid") Long uid, Pageable pageable);
}
