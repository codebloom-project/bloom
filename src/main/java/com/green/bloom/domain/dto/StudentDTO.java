package com.green.bloom.domain.dto;

import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.domain.entity.StudentEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
	
	private long stuNo;
	
	private String stuName;
	private String stuPhone;
	private String stuBirth;
	private String stuSitu;
	
	private String proName;
	
	private ProcedureDTO procedureDTO;
	
	public StudentEntity toStudentEntity(ProcedureEntity procedureEntity) {
		return StudentEntity.builder()
				.stuNo(stuNo)
				.stuName(stuName)
				.stuPhone(stuPhone)
				.stuBirth(stuBirth)
				.stuSitu(stuSitu)
				.procedureEntity(procedureEntity)
				.build();
	}
	
}
