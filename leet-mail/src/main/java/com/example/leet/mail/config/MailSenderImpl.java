package com.example.leet.mail.config;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@EnableAsync
public class MailSenderImpl implements MailSender{

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender javaMailSender){

        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendTextMail(){
        try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom("prashant.kumar_cs19@gla.ac.in");
            mimeMessage.setRecipients(Message.RecipientType.TO,"prashant.kuincy@gmail.com");

            mimeMessage.setText("Hi there");
            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
