package com.example.leet.service.fetch;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.constants.ApplicationConstants;
import com.example.leet.dao.converter.ContestDetailConverter;
import com.example.leet.objects.Contest;
import com.example.leet.objects.ContestUserDetails;
import com.example.leet.service.contest.ContestService;

@EnableAsync
@Service
public class RanksFetchAsyncServiceImpl implements RanksFetchAsyncService {
	private static final Logger log = LoggerFactory.getLogger(RanksFetchAsyncServiceImpl.class);
	
	private final RanksFetchAsyncService ranksFetchAsyncService;
	private final LeetcodeRestClient leetcodeRestClient;
	private final ContestService contestService;
	
	@Value("${async.threads.for.rank.fetch}")
    private int totalThreads;
    
    @Autowired
	public RanksFetchAsyncServiceImpl(@Lazy RanksFetchAsyncService ranksFetchAsyncService,
			LeetcodeRestClient leetcodeRestClient, ContestService contestService) {
		this.ranksFetchAsyncService = ranksFetchAsyncService;
		this.leetcodeRestClient = leetcodeRestClient;
		this.contestService = contestService;
	}

	@Override
    public void fetchContestDetails() {
        int pageNo =1;
        List<ContestUserDetails> contestUserDetails = new LinkedList<>();
        Contest contest = leetcodeRestClient.getContestDetails(pageNo);
        contestUserDetails.addAll(contest.getTotal_rank());
		contestService.saveContestRankList(ContestDetailConverter.dtoListToEntityList(contestUserDetails));

        scalingFetchProcessInThreads(contest.getUser_num());
    }

    /**
     * Method for scaling the ranks fetch process
     * @param limit
     */
    private void scalingFetchProcessInThreads(int totalUsers) {
    	log.info("Fetching leetcode contest ranks for {} users", totalUsers);
        int limit = (int) Math.ceil( (float) totalUsers/ApplicationConstants.pageSize);
    	int batcheSize = (limit-1)/totalThreads;
    	int threadCount=1;
    	int startPageNo = 2;
    	while(threadCount <= totalThreads) {
    		ranksFetchAsyncService.fetchRanksInBatches(startPageNo, threadCount == totalThreads ? limit : startPageNo+batcheSize-1);
    		startPageNo+=batcheSize;
    		threadCount++;
    	}
	}
	
	@Async
	@Override
	public void fetchRanksInBatches(int startPageNo, int endPageNo) {
		log.info("Rank fetch started for batch from {} to {}", startPageNo, endPageNo);
		List<ContestUserDetails> contestUserDetails = new LinkedList<>();
		while(startPageNo<=endPageNo) {
			contestUserDetails.addAll(leetcodeRestClient.getContestDetails(startPageNo).getTotal_rank());
			startPageNo++;
		}
		
		contestService.saveContestRankList(ContestDetailConverter.dtoListToEntityList(contestUserDetails));
		log.info("Rank fetch ended for batch from {} to {}", startPageNo, endPageNo);
	}
	
	
}
