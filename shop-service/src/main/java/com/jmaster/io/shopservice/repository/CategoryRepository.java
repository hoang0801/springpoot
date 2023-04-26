package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT c FROM Category c WHERE c.title LIKE :title ")
    Page<Category> find(@Param("title") String value, Pageable pageable);
}
