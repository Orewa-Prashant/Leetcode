package com.example.leet.cron.subscription;

import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.service.fetch.LeetcodeFetchAsyncService;
import com.example.leet.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class NotifyAboutSubscribedUsersJob {

    private final LeetcodeFetchAsyncService leetcodeFetchAsyncService;
    private final UserService userService;

    @Autowired
    public NotifyAboutSubscribedUsersJob(LeetcodeFetchAsyncService leetcodeFetchAsyncService, UserService userService){

        this.leetcodeFetchAsyncService = leetcodeFetchAsyncService;
        this.userService = userService;
    }
    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 1000*10)
    public void fetchLeetcodeUsersInfo(){
        for(SubscribedUser user : userService.getAllSubscribedUsers()){
            leetcodeFetchAsyncService.getLCUserActivity(user);
        }
    }
}
