package com.example.leet.dao.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.example.leet.dao.entity.ContestDetail;
import com.example.leet.dao.repo.ContestDetailsRepository;
import com.example.leet.objects.Contest;
import com.example.leet.objects.ContestUserDetails;

public class ContestDetailConverter {

    public static ContestDetail dtoToEntity(ContestUserDetails contestUserDetails){
        ContestDetail contestDetails = new ContestDetail();
        contestDetails.setRank(contestUserDetails.getRank());
        contestDetails.setSocre(contestUserDetails.getScore());
        contestDetails.setUsername(contestUserDetails.getUsername());
        contestDetails.setFinishTime(contestUserDetails.getFinish_time());

        return contestDetails;
    }

    public static List<ContestDetail> dtoListToEntityList(List<ContestUserDetails> contestUserDetails){
        return null == contestUserDetails ? null :
                contestUserDetails.stream().map(ContestDetailConverter::dtoToEntity).collect(Collectors.toList());
    }
}
