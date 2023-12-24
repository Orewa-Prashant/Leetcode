package com.example.leet.cron.subscription;

import com.example.leet.service.fetch.RanksFetchAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class NotifyAboutSubscribedUsersJob {

    private final RanksFetchAsyncService ranksFetchAsyncService;

    @Autowired
    public NotifyAboutSubscribedUsersJob(RanksFetchAsyncService ranksFetchAsyncService){

        this.ranksFetchAsyncService = ranksFetchAsyncService;
    }
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void fetchLeetcodeUsersInfo(){
//        ranksFetchAsyncService.
    }
}
