package com.example.leet.service.contest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leet.dao.entity.ContestDetail;
import com.example.leet.dao.repo.ContestDetailsRepository;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService{

    private final ContestDetailsRepository contestDetailsRepository;

    @Autowired
    public ContestServiceImpl(ContestDetailsRepository contestDetailsRepository) {
        this.contestDetailsRepository = contestDetailsRepository;
    }

    @Override
	public void saveContestRankList(List<ContestDetail> contestDetails) {
		contestDetailsRepository.saveAll(contestDetails);
	}
}
