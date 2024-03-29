package com.example.leet.service.users;

import com.example.leet.dao.Activity;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.dao.entity.SubscribedUserActivity;
import com.example.leet.dao.repo.SubscribedUserActivityRepository;
import com.example.leet.dao.repo.SubscribedUserRepository;
import com.example.leet.objects.bo.*;
import com.example.leet.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class LCUserActivtyServiceImpl implements LCUserActivityService{

    private final Logger log = LoggerFactory.getLogger(LCUserActivtyServiceImpl.class);
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
        long lastActivity = null == subscribedUserActivity.getLastActivity() ? 0 : subscribedUserActivity.getLastActivity();
        List<LCUserAcSolution> solutionsToNotify = new ArrayList<>();
        if(null != recentAcSubmissionList && !recentAcSubmissionList.isEmpty()){
            for(LCUserAcSolution acceptedSolution : recentAcSubmissionList){
                if(lastActivity < Long.parseLong(acceptedSolution.getId())){
                    solutionsToNotify.add(acceptedSolution);
                } else break;
            }
            subscribedUserActivity.setLastActivity(Long.parseLong(recentAcSubmissionList.get(0).getId()));
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
            return;
        }
        if(!solutionsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), solutionsToNotify);
        } else{
            log.info("No posts to notify for {}", subscribedUser.getLeetcodeUsername());
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
        long lastActivity = null == subscribedUserActivity.getLastActivity() ? 0 : subscribedUserActivity.getLastActivity();
        List<Node> solutionPostsToNotify = new ArrayList<>();
        if(null != userSolutionTopics && null!=userSolutionTopics.getEdges() && !userSolutionTopics.getEdges().isEmpty()){
            for(Edge userSolutionPostEdge : userSolutionTopics.getEdges()){
                if(lastActivity < Long.parseLong(userSolutionPostEdge.getNode().getId())){
                    solutionPostsToNotify.add(userSolutionPostEdge.getNode());
                } else break;
            }
            subscribedUserActivity.setLastActivity(Long.parseLong(userSolutionTopics.getEdges().get(0).getNode().getId()));
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
            return;
        }
        if(!solutionPostsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), solutionPostsToNotify);
        } else{
            log.info("No posts to notify for {}", subscribedUser.getLeetcodeUsername());
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
        long lastActivity = null == subscribedUserActivity.getLastActivity() ? 0 : subscribedUserActivity.getLastActivity();
        List<Node> discussPostsToNotify = new ArrayList<>();
        if(null != userCategoryTopics && null!=userCategoryTopics.getEdges() && !userCategoryTopics.getEdges().isEmpty()){
            for(Edge userSolutionPostEdge : userCategoryTopics.getEdges()){
                if(lastActivity<Long.parseLong(userSolutionPostEdge.getNode().getId())){
                    discussPostsToNotify.add(userSolutionPostEdge.getNode());
                } else break;
            }
            subscribedUserActivity.setLastActivity(Long.parseLong(userCategoryTopics.getEdges().get(0).getNode().getId()));
        }
        if(savingInfoForFirstTime){
            subscribedUserActivityRepository.save(subscribedUserActivity);
            return;
        }
        if(!discussPostsToNotify.isEmpty()){
            mailService.notifyAboutSubscribedUsers(subscribedUserActivity.getSubscribedUser(), discussPostsToNotify);
        } else{
            log.info("No posts to notify for {}", subscribedUser.getLeetcodeUsername());
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
