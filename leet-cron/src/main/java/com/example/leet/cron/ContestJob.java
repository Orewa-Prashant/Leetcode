package com.example.leet.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.leet.service.fetch.LeetcodeFetchAsyncService;

@EnableScheduling
@Component
public class ContestJob {

	private final LeetcodeFetchAsyncService leetcodeFetchAsyncService;

	@Autowired
	public ContestJob(LeetcodeFetchAsyncService leetcodeFetchAsyncService) {
		this.leetcodeFetchAsyncService = leetcodeFetchAsyncService;
	}

	@Scheduled(cron = "0 25 19 * * ?", zone = "IST")
	public void fetchRanksOfContest() {
		leetcodeFetchAsyncService.fetchContestDetails();
	}
}
