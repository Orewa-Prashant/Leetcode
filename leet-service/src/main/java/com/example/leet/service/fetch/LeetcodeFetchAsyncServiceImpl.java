package com.example.leet.service.fetch;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.objects.Contest;
import com.example.leet.objects.ContestUserDetails;
import com.example.leet.objects.bo.GraphQLPayload;
import com.example.leet.objects.bo.GraphQLResponseBO;
import com.example.leet.objects.bo.Variable;
import com.example.leet.objects.converter.ContestDetailConverter;
import com.example.leet.service.contest.ContestService;
import com.example.leet.service.users.LCUserActivityService;
import com.example.leet.utils.constants.ApplicationConstants;
import com.example.leet.utils.constants.LeetcodeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@EnableAsync
@Service
public class LeetcodeFetchAsyncServiceImpl implements LeetcodeFetchAsyncService {
	private static final Logger log = LoggerFactory.getLogger(LeetcodeFetchAsyncServiceImpl.class);
	
	private final LeetcodeFetchAsyncService leetcodeFetchAsyncService;
	private final LeetcodeRestClient leetcodeRestClient;
	private final ContestService contestService;
	private final LCUserActivityService lcUserActivityService;
	
	@Value("${async.threads.for.rank.fetch}")
    private int totalThreads;
    
    @Autowired
	public LeetcodeFetchAsyncServiceImpl(@Lazy LeetcodeFetchAsyncService leetcodeFetchAsyncService,
										 LeetcodeRestClient leetcodeRestClient, ContestService contestService, LCUserActivityService lcUserActivityService) {
		this.leetcodeFetchAsyncService = leetcodeFetchAsyncService;
		this.leetcodeRestClient = leetcodeRestClient;
		this.contestService = contestService;
		this.lcUserActivityService = lcUserActivityService;
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
     * @param totalUsers
     */
    private void scalingFetchProcessInThreads(int totalUsers) {
    	log.info("Fetching leetcode contest ranks for {} users", totalUsers);
        int limit = (int) Math.ceil( (float) totalUsers/ ApplicationConstants.pageSize);
    	int batcheSize = (limit-1)/totalThreads;
    	int threadCount=1;
    	int startPageNo = 2;
    	while(threadCount <= totalThreads) {
    		leetcodeFetchAsyncService.fetchRanksInBatches(startPageNo, threadCount == totalThreads ? limit : startPageNo+batcheSize-1);
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

	@Async
	@Override
	public void getLCUserActivity(SubscribedUser subscribedUser){
		GraphQLPayload payload = new GraphQLPayload();
		payload.setQuery(LeetcodeConstants.FETCH_USER_ACTIVITY_STRING);
		Variable variable = new Variable();
		variable.setFirst(20);
		variable.setLimit(20);
		variable.setSkip(0);
		variable.setUsername(subscribedUser.getLeetcodeUsername());
		variable.setOrderBy(LeetcodeConstants.NEWEST_TO_OLDEST_SORT_ORDER);
		payload.setVariables(variable);

		GraphQLResponseBO userActivityOnLeetcode = leetcodeRestClient.getLCUsersRecentActivity(payload);
		lcUserActivityService.processSubscribedUserActivity(subscribedUser, userActivityOnLeetcode);
	}
}
