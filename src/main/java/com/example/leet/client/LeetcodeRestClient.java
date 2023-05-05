package com.example.leet.client;

import org.springframework.stereotype.Component;

import com.example.leet.objects.Contest;

@Component
public class LeetcodeRestClient extends BaseRestClient{

    public Contest getContestDetails(String url){
        return getRestTemplate().getForObject(url, Contest.class);
    }
}
