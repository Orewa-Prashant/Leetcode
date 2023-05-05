package com.example.leet.objects;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Submission {
	private Object question;
	
	@JsonAnyGetter
	public Map<String,Object> any(){
		return Collections.singletonMap(question.getClass().getName(), question);
	}
}
