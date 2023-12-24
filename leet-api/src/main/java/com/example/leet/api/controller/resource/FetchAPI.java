package com.example.leet.api.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leet.service.fetch.RanksFetchAsyncService;

@RestController
@RequestMapping("rest/fetch")
public class FetchAPI {
	
	private final RanksFetchAsyncService ranksFetchAsyncService;

	@Autowired
	public FetchAPI(RanksFetchAsyncService ranksFetchAsyncService) {
		this.ranksFetchAsyncService = ranksFetchAsyncService;
	}
	
	@GetMapping("ranks")
	public void fetchRanks() {
		ranksFetchAsyncService.fetchContestDetails();
	}
	
}
