package com.green.bloom.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.green.bloom.domain.dto.CommuteDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Commute")
@Entity
public class CommuteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private LocalDateTime gtwTime;//출근시간
	private LocalDateTime gowTime;//퇴근시간
	
	
	public CommuteEntity update(LocalDateTime gowTime) {
		this.gowTime=gowTime;
		return this;
	}
	@ManyToOne
	@JoinColumn(name = "employee_empNo", nullable = false)
    private EmployeeEntity employee;
	public CommuteDTO toCommuteDTO() {
		
		return CommuteDTO.builder()
				.gtwTime(gtwTime)
				.gowTime(gowTime)
				.empNo(employee.getEmpNo())
				.build();
	}
}
