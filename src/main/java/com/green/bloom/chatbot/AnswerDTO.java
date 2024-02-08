package com.green.bloom.chatbot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDTO {
	
	private long no;
	private String content;
	private String keyword;//name
	private String name;//사용자 이름
	
	private List<PhoneInfo> phone;
	
	private List<DeptInfo> dept;
	
	private List<ScheduleInfo> schedule;
	
	public AnswerDTO phone(List<PhoneInfo> phone){
		this.phone=phone;
		return this;
	}
	
	public AnswerDTO dept(List<DeptInfo> dept) {
		this.dept=dept;
		return this;
	}

	public AnswerDTO schedule(List<ScheduleInfo> schedule) {
		this.schedule=schedule;
		return this;
	}


}