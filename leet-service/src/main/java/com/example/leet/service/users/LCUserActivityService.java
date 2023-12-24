package com.example.leet.service.users;

import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.objects.bo.GraphQLResponseBO;

public interface LCUserActivityService {
    void processSubscribedUserActivity(SubscribedUser subscribedUser, GraphQLResponseBO responseBO);
}
