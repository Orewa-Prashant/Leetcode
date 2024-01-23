package com.example.leet.service.notification;

import com.example.leet.dao.entity.Notification;
import com.example.leet.dao.repo.NotificationStackRepository;
import com.example.leet.dao.specs.NotificationStackSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotficationService {

    private final NotificationStackRepository notificationStackRepository;

    public NotificationServiceImpl(NotificationStackRepository notificationStackRepository) {
        this.notificationStackRepository = notificationStackRepository;
    }

    @Override
    public Page<Notification> getAppUserNotifications(Long appUserId, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc("id")));
        Specification<Notification> specs = Specification.where(NotificationStackSpecs.byAppUserId(appUserId));
        return notificationStackRepository.findAll(specs, pageable);
    }
}
