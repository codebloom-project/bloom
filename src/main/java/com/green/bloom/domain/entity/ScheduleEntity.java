package com.green.bloom.domain.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.green.bloom.chatbot.ScheduleInfo;
import com.green.bloom.domain.dto.ScheduleDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "Schedule")
public class ScheduleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	@Column(nullable = false)
	private boolean categori;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	@Column(nullable = false, name = "scheduleStart")
	private LocalDateTime start;
	@Column(nullable = false, name = "scheduleEnd")
	private LocalDateTime end;
	
//	@ManyToOne
//	@JoinColumn(name = "employee_empNo", nullable = false)
//	private EmployeeEntity emps;
	
	public ScheduleDTO toDTO() {
		return ScheduleDTO.builder()
				.no(no)
//				.empNo(emps.getEmpNo())
				.categori(categori)
				.title(title)
				.content(content)
				.start(start)
				.end(end)
				.build();
	}
	
	public ScheduleInfo toSchedule() {
		return ScheduleInfo.builder()
				.title(title)
				.categori(categori)
				.start(start)
				.end(end)
				.build();
	}
	
	
}
