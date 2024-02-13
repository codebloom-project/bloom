package com.green.bloom.domain.entity;

import java.time.LocalDate;
import java.util.List;

import com.green.bloom.domain.dto.VotingDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "Voting")
public class VotingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private LocalDate start;
	@Column(nullable = false)
	private LocalDate end;
	@Column(nullable = false)
	private String department;
	@Column(nullable = false)
	private String option;
	@Column(nullable = false)
	private boolean votingStyle;
	
	public VotingDTO toDTO() {
		return VotingDTO.builder()
				.no(no)
				.title(title)
				.category(category)
				.content(content)
				.start(start)
				.end(end)
				.department(department)
				.option(option)
				.votingStyle(votingStyle)
				.build();
	}
}
