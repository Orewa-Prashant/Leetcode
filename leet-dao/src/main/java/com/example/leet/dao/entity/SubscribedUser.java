package com.example.leet.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subscribed_user")
public class SubscribedUser {

    private Integer subsUserId;
    private String leetcodeUsername;
    private Set<AppUser> appUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_user_id")
    public Integer getSubsUserId() {
        return subsUserId;
    }

    public void setSubsUserId(Integer subsUserId) {
        this.subsUserId = subsUserId;
    }

    @Column(name = "leetcode_username")
    public String getLeetcodeUsername() {
        return leetcodeUsername;
    }

    public void setLeetcodeUsername(String leetcodeUsername) {
        this.leetcodeUsername = leetcodeUsername;
    }

    @ManyToMany(mappedBy = "subscribedUsers")
    @JsonIgnore
    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
