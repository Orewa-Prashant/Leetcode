package com.example.leet.dao.repo;

import com.example.leet.dao.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationStackRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
}
