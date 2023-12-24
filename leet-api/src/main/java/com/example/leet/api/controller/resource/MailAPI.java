package com.example.leet.api.controller.resource;

import com.example.leet.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/mail")
public class MailAPI {

    private final MailService mailService;

    @Autowired
    public MailAPI(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("send")
    public void sendMail(){
        mailService.sendDummyMail();
    }
}
