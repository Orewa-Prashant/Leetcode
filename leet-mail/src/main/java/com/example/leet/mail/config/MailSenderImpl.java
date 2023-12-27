package com.example.leet.mail.config;

import com.example.leet.dao.entity.AppUser;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.objects.bo.LCUserAcSolution;
import com.example.leet.objects.bo.Node;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
            "<p>%s</p>\n" +
            "<ol>\n" +
            "%s" +
            "</ol>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
    private String ac_solution_list_item = "<li>%s | <a href=%s>AC solution</a></li>\n";

    private String acSolutionBodyText = "Hey User, we just found out that %s just got accepted submissions on Leetcode for following problems";

    private String solutionPostBodyText = "Hey User, we just found out that %s just posted in Discuss on Leetcode for following problems";

    private String discuss_post_list_item = "<li>%s | <a href=%s>Discuss post url</a></li>\n";
    private String acSolutionURIPrefix = "https://leetcode.com/submissions/detail/%s";
    private String discussPostUrl = "https://leetcode.com%s";

    @Value("${spring.mail.username}")
    private String from_email;
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
    public void sendNotification(SubscribedUser subscribedUser, Object postsToNotify) {
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

    private String prepareMailHtmlBody(SubscribedUser subscribedUser, Object postsToNotify) {
        return String.format(htmlBody, fun(subscribedUser.getLeetcodeUsername(), postsToNotify), formHtmlOrderedList(postsToNotify));
    }

    private String fun(String username, Object postsToNotify){
        if(postsToNotify instanceof List list){
            if(list.size() > 0 && list.get(0) instanceof Node){
                return String.format(solutionPostBodyText, username);
            } else if (list.size() > 0 && list.get(0) instanceof LCUserAcSolution){
                return String.format(acSolutionBodyText, username);
            }
        }
        throw new RuntimeException("No Instance type matched, abort mail");
    }

    private String formHtmlOrderedList(Object postsToNotify) {
        StringBuilder sb = new StringBuilder();
        List<Object> data = new ArrayList<>();
        if(postsToNotify instanceof List){
            data = (List<Object>)postsToNotify;
        }
        for (Object object : data) {
            if(object instanceof LCUserAcSolution acSolution){
                sb.append(String.format(ac_solution_list_item,acSolution.getTitle(),String.format(acSolutionURIPrefix, acSolution.getId())));
            } else if(object instanceof Node node){
                sb.append(String.format(discuss_post_list_item, node.getQuestionTitle(), String.format(discussPostUrl,node.getUrl())));
            }
        }
        return sb.toString();
    }

    private void getInstanceOfListObjects(List<Object> postsToNotify) {
//        return postsToNotify.get(0) instanceof LCUserAcSolution
    }

}
