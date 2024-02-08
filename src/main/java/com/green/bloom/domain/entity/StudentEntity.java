package com.green.bloom.domain.entity;

import com.green.bloom.domain.dto.StudentDTO;

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

@SequenceGenerator(name = "gen_seq_stu", sequenceName = "seq_stu", initialValue = 1, allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Students")
public class StudentEntity {
	
	@Id
	@GeneratedValue(generator = "gen_seq_stu", strategy = GenerationType.SEQUENCE)
	private long stuNo;
	
	@Column(nullable = false)
	private String stuName;
	
	@Column(nullable = false)
	private String stuPhone;
	
	@Column(nullable = false)
	private String stuBirth;
	
	@Column(columnDefinition = "VARCHAR(255) default '재원중'")
	private String stuSitu;
	
	@ManyToOne
    @JoinColumn(name = "proNo")  // Employee 테이블과의 관계를 나타내는 외래키
    private ProcedureEntity procedureEntity;

	
    public StudentDTO toStudentDTO() {
    	return StudentDTO.builder()
    			.stuNo(stuNo)
    			.stuName(stuName)
    			.stuPhone(stuPhone)
    			.stuBirth(stuBirth)
    			.stuSitu(stuSitu)
    			.proName(procedureEntity.getProName())
    			.build();
    }


	public void detachProcedureEntity() {
		// ProcedureEntity와의 연결을 해제
        this.procedureEntity = null;
	}
    

}
