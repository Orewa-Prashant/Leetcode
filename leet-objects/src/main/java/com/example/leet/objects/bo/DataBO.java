package com.example.leet.objects.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataBO {

    private LCUserPublishedSolutions userSolutionTopics;
    private LCUserDiscussTopic userCategoryTopics;
    private List<LCUserAcSolution> recentAcSubmissionList;

    public LCUserPublishedSolutions getUserSolutionTopics() {
        return userSolutionTopics;
    }

    public void setUserSolutionTopics(LCUserPublishedSolutions userSolutionTopics) {
        this.userSolutionTopics = userSolutionTopics;
    }

    public LCUserDiscussTopic getUserCategoryTopics() {
        return userCategoryTopics;
    }

    public void setUserCategoryTopics(LCUserDiscussTopic userCategoryTopics) {
        this.userCategoryTopics = userCategoryTopics;
    }

    public List<LCUserAcSolution> getRecentAcSubmissionList() {
        return recentAcSubmissionList;
    }

    public void setRecentAcSubmissionList(List<LCUserAcSolution> recentAcSubmissionList) {
        this.recentAcSubmissionList = recentAcSubmissionList;
    }
}
