package com.example.leet.mail.config;

import com.example.leet.dao.entity.AppUser;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.objects.bo.LCUserAcSolution;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Component
@EnableAsync
public class MailSenderImpl implements MailSender{

    private String htmlBody = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>hehh</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<p>Hey User, we just found out that %s just got accepted submissions on Leetcode for following problems</p>\n" +
            "<ol>\n" +
            "%s" +
            "</ol>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
    private String list_item = "<li>%s | <a href=%s>AC solution</a></li>\n";
    private String acSolutionURIPrefix = "https://leetcode.com/submissions/detail/%s";

    private final String from_email = "prashant.kumar1_cs19@gla.ac.in";
    private String subject = "%s's activity on Leetcode";

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender javaMailSender){

        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendTextMail(){
        try {
            MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()));
            mimeMessage.setFrom("prashant.kumar_cs19@gla.ac.in");
            mimeMessage.setRecipients(Message.RecipientType.TO,"prashant.kuincy@gmail.com");

            mimeMessage.setText("Hi there");
            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void sendNotification(SubscribedUser subscribedUser, List<Object> postsToNotify) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setBcc(subscribedUser.getAppUsers().stream().map(AppUser::getEmail).toArray(String[]::new));
            mimeMessageHelper.setFrom(from_email);
            mimeMessageHelper.setSubject(getSubject(subscribedUser));
            mimeMessageHelper.setText(prepareMailHtmlBody(subscribedUser,postsToNotify), true);
        };
        javaMailSender.send(mimeMessagePreparator);
    }
    private String getSubject(SubscribedUser subscribedUser){
        return String.format(subject, subscribedUser.getLeetcodeUsername());
    }

    private String prepareMailHtmlBody(SubscribedUser subscribedUser, List<Object> postsToNotify) {
        return String.format(htmlBody, subscribedUser.getLeetcodeUsername(), formHtmlOrderedList(postsToNotify));
    }

    private String formHtmlOrderedList(List<Object> postsToNotify) {
        StringBuilder sb = new StringBuilder();
        Object solutions = postsToNotify.get(0);
        postsToNotify = (List<Object>) solutions;
        for (Object object : postsToNotify) {
            if(object instanceof LCUserAcSolution acSolution){
                sb.append(String.format(list_item,acSolution.getTitle(),String.format(acSolutionURIPrefix, acSolution.getId())));
            }
        }
        return sb.toString();
    }

    private void getInstanceOfListObjects(List<Object> postsToNotify) {
//        return postsToNotify.get(0) instanceof LCUserAcSolution
    }

}
