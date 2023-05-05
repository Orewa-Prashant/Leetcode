package com.example.leet.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.leet.service.contest.ContestService;

@EnableScheduling
@Component
public class ContestJob {

	private final ContestService contestService;

	@Autowired
	public ContestJob(ContestService contestService) {
		this.contestService = contestService;
	}

	@Scheduled(cron = "0 25 19 * * ?", zone = "IST")
	public void fetchRanksOfContest() {
		contestService.fetchContestDetails();
	}
}
