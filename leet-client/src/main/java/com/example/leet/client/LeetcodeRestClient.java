package com.example.leet.client;

import com.example.leet.objects.Contest;
import com.example.leet.objects.bo.GraphQLPayload;
import com.example.leet.objects.bo.GraphQLResponseBO;
import com.example.leet.utils.constants.ApplicationConstants;
import com.example.leet.utils.constants.LeetcodeConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class LeetcodeRestClient extends BaseRestClient{

    public boolean isUserOnLeetcode(String username){
        try {
            getRestTemplate().getForObject(String.format(LeetcodeConstants.PROFILE_URL_PREFIX, username), String.class);
        } catch (HttpClientErrorException.NotFound e){
            return false;
        }
        return true;
    }

    public Contest getContestDetails(int pageNo){
    	return getRestTemplate().getForObject(formAPIUrl(pageNo), Contest.class);
    }
    
    private String formAPIUrl(int pageNo){
        return LeetcodeConstants.LEETCODE_BIWEEKLY_CONTEST+ ApplicationConstants.QUESTION_DELIMITER+
                LeetcodeConstants.QUERY_PAGINATION+ApplicationConstants.EQUAL_DELIMITER+pageNo+ApplicationConstants.AND_DELIMITER+
                LeetcodeConstants.QUERY_REGION+ApplicationConstants.EQUAL_DELIMITER+"global";
    }

    public GraphQLResponseBO getLCUsersRecentActivity(String uri, Object request){
        ResponseEntity<GraphQLResponseBO> response = getRestTemplate().postForEntity(uri, request, GraphQLResponseBO.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        } else {
            return null;
        }
    }
}
