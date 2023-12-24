package com.example.leet.service.fetch;

public interface RanksFetchAsyncService {

	void fetchContestDetails();
	
	void fetchRanksInBatches(int startPageNo, int endPageNo);

}
