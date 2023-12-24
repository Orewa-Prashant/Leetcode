package com.example.leet.dao.repo;

import com.example.leet.dao.entity.SubscribedUserActivity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscribedUserActivityRepository extends JpaRepository<SubscribedUserActivity, Integer> {
}
