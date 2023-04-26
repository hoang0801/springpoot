package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface BillRepository extends JpaRepository<Bill,Long> {
    @Query("SELECT b FROM Bill b JOIN b.createdBy c WHERE c.username = :username ")
    Page<Bill> find(@Param("username") String username, Pageable pageable);
}
