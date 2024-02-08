package com.green.bloom.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.green.bloom.domain.entity.CommuteEntity;
import com.green.bloom.domain.entity.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommuteDTO {
	
	private long no;
	private LocalDateTime gtwTime;
	private LocalDateTime gowTime;
	private long empNo;
	
	public CommuteEntity toEntity(EmployeeEntity employeeEntity) {
		return CommuteEntity.builder()
				.no(no)
				.gtwTime(gtwTime)
				.gowTime(gowTime)
				.employee(employeeEntity)
				.build();
	}

}
