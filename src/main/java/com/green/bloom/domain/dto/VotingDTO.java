package com.green.bloom.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.green.bloom.domain.entity.VotingEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotingDTO {

	private long no;
	private String title;
	private String content;
	private String category;
	private LocalDate start;
	private LocalDate end;
	private String department;
	private String option;
	private boolean votingStyle;
	
	public VotingEntity toEntity() {
		return VotingEntity.builder()
				.no(no)
				.title(title)
				.content(content)
				.category(category)
				.start(start)
				.end(end)
				.department(department)
				.option(option)
				.votingStyle(votingStyle)
				.build();
	}
}
