package com.example.leet.dao.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Notification.class)
public class Notification_ {
    public static volatile SingularAttribute<Notification, AppUser> appUser;

}
