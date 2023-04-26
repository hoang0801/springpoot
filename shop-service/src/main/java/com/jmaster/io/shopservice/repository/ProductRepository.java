package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.title LIKE :title ")
    Page<Product> find(@Param("title") String value, Pageable pageable);

}
