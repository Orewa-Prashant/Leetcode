package com.example.leet.api.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leet.service.fetch.LeetcodeFetchAsyncService;

@RestController
@RequestMapping("rest/fetch")
public class FetchAPI {
	
	private final LeetcodeFetchAsyncService leetcodeFetchAsyncService;

	@Autowired
	public FetchAPI(LeetcodeFetchAsyncService leetcodeFetchAsyncService) {
		this.leetcodeFetchAsyncService = leetcodeFetchAsyncService;
	}
	
	@GetMapping("ranks")
	public void fetchRanks() {
		leetcodeFetchAsyncService.fetchContestDetails();
	}
	
}
