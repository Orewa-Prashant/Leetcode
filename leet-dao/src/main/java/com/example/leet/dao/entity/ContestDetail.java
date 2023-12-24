package com.example.leet.dao.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "contest_detail")
public class ContestDetail implements Serializable {

	private static final long serialVersionUID = 173200673154058914L;

	private Integer contestDetailId;

	private String username;

	private Integer rank;

	private Byte socre;

	private Date finishTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_id")
	public Integer getContestDetailId() {
		return contestDetailId;
	}

	public void setContestDetailId(Integer contestDetailId) {
		this.contestDetailId = contestDetailId;
	}
	
	@Column(name = "user_name")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "rank")
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "score")
	public Byte getSocre() {
		return socre;
	}

	public void setSocre(Byte socre) {
		this.socre = socre;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finish_time")
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
