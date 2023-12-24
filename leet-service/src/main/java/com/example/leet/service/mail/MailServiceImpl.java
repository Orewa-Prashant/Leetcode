package com.example.leet.service.mail;

import com.example.leet.mail.config.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
