package com.green.bloom.domain.dto;

import com.green.bloom.domain.entity.StudentAtt;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentAttDTO {
	
	private boolean isPresent;
	
	public StudentAtt toEntity() {
		return StudentAtt.builder()
				.isPresent(isPresent)
				.build();
	}
}
