package com.example.leet.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.leet.service.contest.ContestService;
import com.example.leet.service.fetch.RanksFetchAsyncService;

@EnableScheduling
@Component
public class ContestJob {

	private final RanksFetchAsyncService ranksFetchAsyncService;

	@Autowired
	public ContestJob(RanksFetchAsyncService ranksFetchAsyncService) {
		this.ranksFetchAsyncService = ranksFetchAsyncService;
	}

	@Scheduled(cron = "0 25 19 * * ?", zone = "IST")
	public void fetchRanksOfContest() {
		ranksFetchAsyncService.fetchContestDetails();
	}
}
