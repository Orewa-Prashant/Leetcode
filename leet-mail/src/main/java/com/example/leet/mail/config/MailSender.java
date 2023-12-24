package com.example.leet.mail.config;

import com.example.leet.dao.entity.SubscribedUser;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailSender {

    void sendTextMail();

    void sendNotification(SubscribedUser subscribedUser, List<Object> postsToNotify);
}
