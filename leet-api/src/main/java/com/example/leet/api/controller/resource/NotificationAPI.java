package com.example.leet.api.controller.resource;

import com.example.leet.dao.entity.Notification;
import com.example.leet.objects.converter.NotificationStackConverter;
import com.example.leet.objects.dto.NotificationDTO;
import com.example.leet.service.notification.NotficationService;
import com.example.leet.utils.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/notification")
public class NotificationAPI {

    private final NotficationService notficationService;

    public NotificationAPI(NotficationService notficationService) {
        this.notficationService = notficationService;
    }

    @GetMapping("{appUserId}")
    public NotificationDTO getUserNotfication(@PathVariable(name = "appUserId") Long appUserId, @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo, @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize){
        NotificationDTO notificationDTO = new NotificationDTO();
        try{
            Page<Notification> stack = notficationService.getAppUserNotifications(appUserId, pageNo, pageSize);
            notificationDTO.setNotificationDTOS(NotificationStackConverter.entityCollectionToDtoCollection(stack.getContent()));
//            notificationDTO.se
        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Some error occured, please try again");
        }
        return notificationDTO;
    }
}
