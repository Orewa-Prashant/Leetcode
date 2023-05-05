package com.example.leet.objects;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ContestUserDetails implements Serializable {
	
	private Integer contest_id;
	private String username;
	private Integer rank;
	private Byte score;
	private Date finish_time;
	public Integer getContest_id() {
		return contest_id;
	}
	public void setContest_id(Integer contest_id) {
		this.contest_id = contest_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Byte getScore() {
		return score;
	}
	public void setScore(Byte score) {
		this.score = score;
	}
	public Date getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}
	
	
}
