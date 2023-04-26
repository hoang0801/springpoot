package com.jmaster.io.shopservice.repository;

import com.jmaster.io.shopservice.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("SELECT n FROM Notification n JOIN n.user u WHERE u.username LIKE :username ")
    Page<Notification> find(@Param("username") String value, Pageable pageable);
}
