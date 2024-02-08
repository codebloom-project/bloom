package com.green.bloom.domain.entity;


import com.green.bloom.chatbot.AnswerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
@Entity
public class Answer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String content;
	
	private String keyword;//name
	
	public Answer keyword(String keyword) {
		this.keyword=keyword;
		return this;
	}
	
	public AnswerDTO toAnswerDTO() {
		return AnswerDTO.builder()
				.no(no).content(content).keyword(keyword)
				.build();
	}

}