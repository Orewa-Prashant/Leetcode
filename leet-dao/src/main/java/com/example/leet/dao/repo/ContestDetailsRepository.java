package com.example.leet.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leet.dao.entity.ContestDetail;

@Repository
public interface ContestDetailsRepository extends JpaRepository<ContestDetail, Integer> {
}
