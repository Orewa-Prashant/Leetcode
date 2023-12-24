package com.example.leet.service.mail;

import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.objects.bo.Node;

import java.util.List;

public interface MailService {

    void sendDummyMail();

    void notifyAboutSubscribedUsers(SubscribedUser subscribedUser, List<Object> discussPostsToNotify);
}
