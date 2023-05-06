package com.example.leet.service.contest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leet.client.LeetcodeRestClient;
import com.example.leet.constants.ApplicationConstants;
import com.example.leet.constants.LeetcodeConstants;
import com.example.leet.dao.converter.ContestDetailConverter;
import com.example.leet.dao.entity.ContestDetail;
import com.example.leet.dao.repo.ContestDetailsRepository;
import com.example.leet.objects.Contest;
import com.example.leet.objects.ContestUserDetails;
import com.example.leet.service.fetch.RanksFetchAsyncService;

import java.beans.Transient;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

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
