package com.example.leet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leet.service.contest.ContestService;

@RestController
@RequestMapping("rest/fetch")
public class FetchAPI {
	
	private final ContestService contestService;

	@Autowired
	public FetchAPI(ContestService contestService) {
		super();
		this.contestService = contestService;
	}
	
	@GetMapping("ranks")
	public void fetchRanks() {
		contestService.fetchContestDetails();
	}
	
}
