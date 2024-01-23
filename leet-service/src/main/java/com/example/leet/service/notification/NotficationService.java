package com.example.leet.service.notification;

import com.example.leet.dao.entity.Notification;
import org.springframework.data.domain.Page;

public interface NotficationService {
    Page<Notification> getAppUserNotifications(Long appUserId, int pageNo, int pageSize);
}
