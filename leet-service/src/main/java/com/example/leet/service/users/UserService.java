package com.example.leet.service.users;

import com.example.leet.dao.entity.AppUser;
import com.example.leet.dao.entity.SubscribedUser;

import java.util.List;

public interface UserService {
    AppUser createUser(AppUser appUser);

    void subscribeToLeetCodeUser(String username, Integer userId);

    List<SubscribedUser> getAllSubscribedUsers();

}
