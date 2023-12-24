package com.example.leet.service.fetch;

import com.example.leet.dao.entity.SubscribedUser;
import org.springframework.scheduling.annotation.Async;

public interface LeetcodeFetchAsyncService {

	void fetchContestDetails();
	
	void fetchRanksInBatches(int startPageNo, int endPageNo);

    void getLCUserActivity(SubscribedUser subscribedUser);
}
