package com.example.leet.service.users;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.dao.entity.AppUser;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.dao.repo.AppUserRepository;
import com.example.leet.dao.repo.SubscribedUserRepository;
import com.example.leet.service.fetch.LeetcodeFetchAsyncService;
import com.example.leet.utils.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AppUserRepository appUserRepository;
    private final SubscribedUserRepository subscribedUserRepository;
    private final LeetcodeRestClient leetcodeRestClient;
    private final LeetcodeFetchAsyncService leetcodeFetchAsyncService;

    public UserServiceImpl(AppUserRepository appUserRepository, SubscribedUserRepository subscribedUserRepository, LeetcodeRestClient leetcodeRestClient, LeetcodeFetchAsyncService leetcodeFetchAsyncService){
        this.appUserRepository = appUserRepository;
        this.subscribedUserRepository = subscribedUserRepository;
        this.leetcodeRestClient = leetcodeRestClient;
        this.leetcodeFetchAsyncService = leetcodeFetchAsyncService;
    }

    @Transactional
    @Override
    public AppUser createUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    @Transactional
    @Override
    public void subscribeToLeetCodeUser(String leetcodeUsername, Integer userId){
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if(appUser.isEmpty()){
            throw new ServiceException("AppUser not present");
        }
        SubscribedUser subscribedUser = subscribedUserRepository.findByLeetcodeUsername(leetcodeUsername);
        if(null == subscribedUser){
            log.info("Leetcode user {} not found in database", leetcodeUsername);
            subscribedUser = createLeetcodeUser(leetcodeUsername);
            leetcodeFetchAsyncService.getLCUserActivity(subscribedUser);
        }
        appUser.get().getSubscribedUsers().add(subscribedUser);
        appUser.get().setSubscribedUsers(appUser.get().getSubscribedUsers());
    }

    @Transactional
    public SubscribedUser createLeetcodeUser(String leetcodeUsername) {
        if(!leetcodeRestClient.isUserOnLeetcode(leetcodeUsername)){
            log.info("Invalid user");
            throw new ServiceException(String.format("No user with %s found on leetcode", leetcodeUsername));
        }
        SubscribedUser subscribedUser = new SubscribedUser();
        subscribedUser.setLeetcodeUsername(leetcodeUsername);
        return subscribedUserRepository.save(subscribedUser);
    }

    @Override
    public List<SubscribedUser> getAllSubscribedUsers(){
        return subscribedUserRepository.findAll();
    }

}
