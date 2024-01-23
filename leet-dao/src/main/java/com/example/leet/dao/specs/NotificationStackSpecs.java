package com.example.leet.dao.specs;

import com.example.leet.dao.entity.Notification;
import com.example.leet.dao.entity.metamodels.AppUser_;
import com.example.leet.dao.entity.metamodels.NotificationStack_;
import org.springframework.data.jpa.domain.Specification;

public class NotificationStackSpecs {

    public static Specification<Notification> byAppUserId(Long appUserId){
        return (root, query, cb) -> {
            return cb.equal(root.get(NotificationStack_.appUser).get(AppUser_.appUserId), appUserId);
        };
    }
}
