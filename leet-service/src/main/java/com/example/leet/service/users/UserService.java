package com.example.leet.service.users;

import com.example.leet.dao.entity.AppUser;

public interface UserService {
    AppUser createUser(AppUser appUser);

    void subscribeToLeetCodeUser(String username);
}
