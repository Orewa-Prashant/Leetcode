package com.example.leet.service.users;

import com.example.leet.dao.Activity;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.dao.entity.SubscribedUserActivity;
import com.example.leet.dao.repo.SubscribedUserActivityRepository;
import com.example.leet.dao.repo.SubscribedUserRepository;
import com.example.leet.objects.bo.*;
import com.example.leet.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class LCUserActivtyServiceImpl implements LCUserActivityService{

    private final SubscribedUserActivityRepository subscribedUserActivityRepository;
    private final SubscribedUserRepository subscribedUserRepository;
    private final MailService mailService;

    @Autowired
    public LCUserActivtyServiceImpl(SubscribedUserActivityRepository subscribedUserActivityRepository, SubscribedUserRepository subscribedUserRepository, MailService mailService) {
        this.subscribedUserActivityRepository = subscribedUserActivityRepository;
        this.subscribedUserRepository = subscribedUserRepository;
        this.mailService = mailService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void processSubscribedUserActivity(SubscribedUser subscribedUser, GraphQLResponseBO response) {
        subscribedUser = subscribedUserRepository.findById(subscribedUser.getSubsUserId()).get();
        Set<SubscribedUserActivity> subscribedUserActivitySet = subscribedUser.getSubscribedUserActivities();


        DataBO data = response.getData();
        if(null == subscribedUserActivitySet || subscribedUserActivitySet.isEmpty()){
            subscribedUserActivitySet = new HashSet<>();
        }
        processDiscussPosts(subscribedUser, subscribedUserActivitySet, data.getUserCategoryTopics());
        processSolutionPosts(subscribedUser, subscribedUserActivitySet, data.getUserSolutionTopics());
        processACSolutions(subscribedUser, subscribedUserActivitySet, data.getRecentAcSubmissionList());
    }

    private void processACSolutions(SubscribedUser subscribedUser, Set<SubscribedUserActivity> subscribedUserActivitySet, List<LCUserAcSolution> recentAcSubmissionList) {
        SubscribedUserActivity subscribedUserActivity = getSubscribedUserActivityOnActivityType(subscribedUserActivitySet, Activity.SUBMISSION);
        boolean savingInfoForFirstTime = false;
        if(null == subscribedUserActivity){
            subscribedUserActivity = new SubscribedUserActivity();
            subscribedUserActivity.setSubscribedUser(subscribedUser);
            subscribedUserActivity.setActivity(Activity.SUBMISSION);
            savingInfoForFirstTime = true;
        }
        List<LCUserAcSolution> solutionsToNotify = new ArrayList<>();
        if(null != recentAcSubmissionList && !recentAcSubmissionList.isEmpty()){
            for(LCUserAcSolution acceptedSolution : recentAcSubmissionList){
                if(null == subscribedUserActivity.getLastActivity() || subscribedUserActivity.getLastActivity()<Long.parseLong(acceptedSolution.getId())){
                    subscribedUserActivity.setLastActivity(Long.parseLong(acceptedSolution.getId()));
                    solutionsToNotify.add(acceptedSolution);
                }
            }
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
        }
        if(!savingInfoForFirstTime && !solutionsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), Collections.singletonList(solutionsToNotify));
        }
    }

    private void processSolutionPosts(SubscribedUser subscribedUser, Set<SubscribedUserActivity> subscribedUserActivitySet, LCUserPublishedSolutions userSolutionTopics) {
        SubscribedUserActivity subscribedUserActivity = getSubscribedUserActivityOnActivityType(subscribedUserActivitySet, Activity.SOLUTION);
        boolean savingInfoForFirstTime = false;
        if(null == subscribedUserActivity){
            subscribedUserActivity = new SubscribedUserActivity();
            subscribedUserActivity.setSubscribedUser(subscribedUser);
            subscribedUserActivity.setActivity(Activity.SOLUTION);
            savingInfoForFirstTime = true;
        }
        List<Node> solutionPostsToNotify = new ArrayList<>();
        if(null != userSolutionTopics && null!=userSolutionTopics.getEdges() && !userSolutionTopics.getEdges().isEmpty()){
            for(Edge userSolutionPostEdge : userSolutionTopics.getEdges()){
                if(null == subscribedUserActivity.getLastActivity() || subscribedUserActivity.getLastActivity()<Long.parseLong(userSolutionPostEdge.getNode().getId())){
                    subscribedUserActivity.setLastActivity(Long.parseLong(userSolutionPostEdge.getNode().getId()));
                    solutionPostsToNotify.add(userSolutionPostEdge.getNode());
                }
            }
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
        }
        if(!savingInfoForFirstTime && !solutionPostsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), Collections.singletonList(solutionPostsToNotify));
        }
    }

    private void processDiscussPosts(SubscribedUser subscribedUser, Set<SubscribedUserActivity> subscribedUserActivitySet, LCUserDiscussTopic userCategoryTopics) {
        SubscribedUserActivity subscribedUserActivity = getSubscribedUserActivityOnActivityType(subscribedUserActivitySet, Activity.DISCUSS);
        boolean savingInfoForFirstTime = false;
        if(null == subscribedUserActivity){
            subscribedUserActivity = new SubscribedUserActivity();
            subscribedUserActivity.setSubscribedUser(subscribedUser);
            subscribedUserActivity.setActivity(Activity.DISCUSS);
            savingInfoForFirstTime = true;
        }
        List<Node> discussPostsToNotify = new ArrayList<>();
        if(null != userCategoryTopics && null!=userCategoryTopics.getEdges() && !userCategoryTopics.getEdges().isEmpty()){
            for(Edge userSolutionPostEdge : userCategoryTopics.getEdges()){
                if(null == subscribedUserActivity.getLastActivity() || subscribedUserActivity.getLastActivity()<Long.parseLong(userSolutionPostEdge.getNode().getId())){
                    subscribedUserActivity.setLastActivity(Long.parseLong(userSolutionPostEdge.getNode().getId()));
                    discussPostsToNotify.add(userSolutionPostEdge.getNode());
                }
            }
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
        }
        if(!savingInfoForFirstTime && !discussPostsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), Collections.singletonList(discussPostsToNotify));
        }
    }

    private SubscribedUserActivity getSubscribedUserActivityOnActivityType(Set<SubscribedUserActivity> subscribedUserActivitySet, Activity activity){
        if(null != subscribedUserActivitySet){
            for (SubscribedUserActivity subscribedUserActivity : subscribedUserActivitySet) {
                if(subscribedUserActivity.getActivity() == activity){
                    return subscribedUserActivity;
                }
            }
        }
        return null;
    }
}
