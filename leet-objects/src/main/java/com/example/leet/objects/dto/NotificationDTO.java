package com.example.leet.objects.dto;

import java.util.Collection;
import java.util.List;

public class NotificationDTO {

    private String description;
    private String link;
    private boolean isOpened;
    private Collection<NotificationDTO> notificationDTOS;
    private Long newNotificationCount;

    public NotificationDTO() {
    }

    public String getDescription() {
        return description;
    }

    public NotificationDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLink() {
        return link;
    }

    public NotificationDTO setLink(String link) {
        this.link = link;
        return this;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public NotificationDTO setOpened(boolean opened) {
        isOpened = opened;
        return this;
    }

    public Collection<NotificationDTO> getNotificationDTOS() {
        return notificationDTOS;
    }

    public void setNotificationDTOS(Collection<NotificationDTO> notificationDTOS) {
        this.notificationDTOS = notificationDTOS;
    }

    public Long getNewNotificationCount() {
        return newNotificationCount;
    }

    public NotificationDTO setNewNotificationCount(Long newNotificationCount) {
        this.newNotificationCount = newNotificationCount;
        return this;
    }
}
