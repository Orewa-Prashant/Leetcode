package com.example.leet.dao.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class AppUser {

    private Integer appUserId;
    private String email;
    private Set<SubscribedUser> subscribedUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscriptions",
            joinColumns = { @JoinColumn(name = "app_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "leet_user_id") }
    )
    public Set<SubscribedUser> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(Set<SubscribedUser> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }
}
