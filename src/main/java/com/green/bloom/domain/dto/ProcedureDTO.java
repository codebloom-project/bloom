package com.green.bloom.domain.dto;

import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.ProcedureEntity;

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
public class ProcedureDTO {

	private long proNo;
	
	private String proName;
	private String proClass;
	private String proType;
	private String proStart;
	private String proEnd;
	
	private String empName;
	
	private EmployeeDTO employeeDTO;
	
	public ProcedureEntity toProcedureEntity(EmployeeEntity employeeEntity) {
		return ProcedureEntity.builder()
				.proNo(proNo)
				.proName(proName)
				.proClass(proClass)
				.proType(proType)
				.proStart(proStart)
				.proEnd(proEnd)
				.employeeEntity(employeeEntity)
				.build();
	}
	
}
