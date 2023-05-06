package com.example.leet.service.contest;

import java.util.List;

import com.example.leet.dao.entity.ContestDetail;

public interface ContestService {

	void saveContestRankList(List<ContestDetail> contestDetails);

}
