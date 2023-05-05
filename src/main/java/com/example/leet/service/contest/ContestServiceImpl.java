package com.example.leet.service.contest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.constants.ApplicationConstants;
import com.example.leet.constants.LeetcodeConstants;
import com.example.leet.dao.converter.ContestDetailConverter;
import com.example.leet.dao.entity.ContestDetail;
import com.example.leet.dao.repo.ContestDetailsRepository;
import com.example.leet.objects.Contest;
import com.example.leet.objects.ContestUserDetails;

import java.beans.Transient;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ContestServiceImpl implements ContestService{

    private final LeetcodeRestClient leetcodeRestClient;
    private final ContestDetailsRepository contestDetailsRepository;

    @Autowired
    public ContestServiceImpl(LeetcodeRestClient leetcodeRestClient, ContestDetailsRepository contestDetailsRepository) {
        this.leetcodeRestClient = leetcodeRestClient;
        this.contestDetailsRepository = contestDetailsRepository;
    }

    @Override
    public void fetchContestDetails() {
        int pageNo =1;
        int limit = 1;
        List<ContestUserDetails> contestUserDetails = new LinkedList<>();
        while(pageNo <= limit){
            Contest contest = leetcodeRestClient.getContestDetails(formAPIUrl(pageNo++));
            contestUserDetails.addAll(contest.getTotal_rank());
            if(limit == 1 ) limit = (int) Math.ceil( (float) contest.getUser_num()/ApplicationConstants.pageSize);
        }

        contestDetailsRepository.saveAll(ContestDetailConverter.dtoListToEntityList(contestUserDetails));
    }

    private String formAPIUrl(int pageNo){
        return LeetcodeConstants.LEETCODE_WEEKLY_CONTEST+ApplicationConstants.QUESTION_DELIMITER+
                LeetcodeConstants.QUERY_PAGINATION+ApplicationConstants.EQUAL_DELIMITER+pageNo+ApplicationConstants.AND_DELIMITER+
                LeetcodeConstants.QUERY_REGION+ApplicationConstants.EQUAL_DELIMITER+"global";
    }
}
