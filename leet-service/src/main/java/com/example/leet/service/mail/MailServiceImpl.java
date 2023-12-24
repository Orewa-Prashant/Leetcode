package com.example.leet.service.mail;

import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.mail.config.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService{

    private final MailSender mailSender;

    @Autowired
    public MailServiceImpl(MailSender mailSender){

        this.mailSender = mailSender;
    }

    @Override
    public void sendDummyMail(){
        mailSender.sendTextMail();
    }

    @Override
    public void notifyAboutSubscribedUsers(SubscribedUser subscribedUser, List<Object> postsToNotify) {
        mailSender.sendNotification(subscribedUser, postsToNotify);
    }
}
