package com.example.leet.dao.entity;

import com.example.leet.dao.entity.AppUser;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AppUser.class)
public class AppUser_ {
    public static SingularAttribute<AppUser, Long> appUserId;
}
