package com.example.leet.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Contest implements Serializable {
	
	private static final long serialVersionUID = -4446450432962279600L;
	//	private Date time;
	private boolean is_past;
	private List<Submission> submissions;
	private List<Question> questions;
	private List<ContestUserDetails> total_rank;
	private Integer user_num;
	
	
//	public Date getTime() {
//		return time;
//	}
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
//	public void setTime(Date time) {
//		this.time = time;
//	}
	public boolean isIs_past() {
		return is_past;
	}
	public void setIs_past(boolean is_past) {
		this.is_past = is_past;
	}
	public List<Submission> getSubmissions() {
		return submissions;
	}
	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public List<ContestUserDetails> getTotal_rank() {
		return total_rank;
	}
	public void setTotal_rank(List<ContestUserDetails> total_rank) {
		this.total_rank = total_rank;
	}
	public Integer getUser_num() {
		return user_num;
	}
	public void setUser_num(Integer user_num) {
		this.user_num = user_num;
	}
	
	
	
}
