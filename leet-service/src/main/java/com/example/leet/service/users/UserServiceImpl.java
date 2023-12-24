package com.example.leet.service.users;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.dao.entity.AppUser;
import com.example.leet.dao.entity.SubscribedUser;
import com.example.leet.dao.repo.AppUserRepository;
import com.example.leet.dao.repo.SubscribedUserRepository;
import com.example.leet.utils.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AppUserRepository appUserRepository;
    private final SubscribedUserRepository subscribedUserRepository;
    private final LeetcodeRestClient leetcodeRestClient;

    public UserServiceImpl(AppUserRepository appUserRepository, SubscribedUserRepository subscribedUserRepository, LeetcodeRestClient leetcodeRestClient){
        this.appUserRepository = appUserRepository;
        this.subscribedUserRepository = subscribedUserRepository;
        this.leetcodeRestClient = leetcodeRestClient;
    }

    @Transactional
    @Override
    public AppUser createUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    @Transactional
    @Override
    public void subscribeToLeetCodeUser(String leetcodeUsername){
        Optional<AppUser> appUser = appUserRepository.findById(1);
        if(appUser.isEmpty()){
            throw new ServiceException("AppUser not present");
        }
        SubscribedUser subscribedUser = subscribedUserRepository.findByLeetcodeUsername(leetcodeUsername);
        if(null == subscribedUser){
            log.info("Leetcode user {} not found in database", leetcodeUsername);
            subscribedUser = createLeetcodeUser(leetcodeUsername);
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
}
