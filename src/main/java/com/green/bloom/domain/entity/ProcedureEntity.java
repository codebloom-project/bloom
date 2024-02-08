package com.green.bloom.domain.entity;

import com.green.bloom.domain.dto.ProcedureDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SequenceGenerator(name = "gen_seq_pro", sequenceName = "seq_pro", initialValue = 1, allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Procedures")
public class ProcedureEntity {
	
	@Id
	@GeneratedValue(generator = "gen_seq_pro", strategy = GenerationType.SEQUENCE)
	private long proNo;
	
	@Column(nullable = false)
	private String proName;
	
	@Column(nullable = false)
	private String proClass;
	
	@Column(nullable = false)
	private String proType;
	
	@Column(nullable = false)
	private String proStart;
	
	@Column(nullable = false)
	private String proEnd;
	
	@ManyToOne
    @JoinColumn(name = "empNo")  // Employee 테이블과의 관계를 나타내는 외래키
    private EmployeeEntity employeeEntity;
	
    
    
	public ProcedureDTO toProcedureDTO() {
		return ProcedureDTO.builder()
				.proNo(proNo)
				.proName(proName)
				.proClass(proClass)
				.proType(proType)
				.proStart(proStart)
				.proEnd(proEnd)
				.empName(employeeEntity.getEmpName())
				.build();
	}

}
