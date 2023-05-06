package com.example.leet.client;

import org.springframework.stereotype.Component;

import com.example.leet.constants.ApplicationConstants;
import com.example.leet.constants.LeetcodeConstants;
import com.example.leet.objects.Contest;

@Component
public class LeetcodeRestClient extends BaseRestClient{

    public Contest getContestDetails(int pageNo){
    	return getRestTemplate().getForObject(formAPIUrl(pageNo), Contest.class);
    }
    
    private String formAPIUrl(int pageNo){
        return LeetcodeConstants.LEETCODE_WEEKLY_CONTEST+ApplicationConstants.QUESTION_DELIMITER+
                LeetcodeConstants.QUERY_PAGINATION+ApplicationConstants.EQUAL_DELIMITER+pageNo+ApplicationConstants.AND_DELIMITER+
                LeetcodeConstants.QUERY_REGION+ApplicationConstants.EQUAL_DELIMITER+"global";
    }
}
