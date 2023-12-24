package com.example.leet.dao.repo;

import com.example.leet.dao.entity.SubscribedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Integer>, JpaSpecificationExecutor<SubscribedUser> {

    SubscribedUser findByLeetcodeUsername(String username);
}
