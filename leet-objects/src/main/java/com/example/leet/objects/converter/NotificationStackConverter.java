package com.example.leet.objects.converter;

import com.example.leet.dao.entity.Notification;
import com.example.leet.objects.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class NotificationStackConverter {

    public static NotificationDTO entityToDto(Notification notificationStack){
        return new NotificationDTO().setDescription(notificationStack.getDescription()).setOpened(notificationStack.isOpened());
    }

    public static Collection<NotificationDTO> entityCollectionToDtoCollection(Collection<Notification> notificationStacks){
        if(notificationStacks == null) return null;
        return notificationStacks.stream().map(NotificationStackConverter::entityToDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
