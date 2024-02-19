package com.green.bloom.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.green.bloom.chatbot.DeptInfo;
import com.green.bloom.chatbot.PhoneInfo;
import com.green.bloom.security.EmployeeRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "gen_emp_seq", sequenceName = "emp_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(generator = "gen_emp_seq", strategy = GenerationType.SEQUENCE)
	private long empNo;
	
	@Column(nullable = false, unique = true)
	private String empUsername;
	
	@Column(nullable = false)
	private String empPassword;
	
	@Column(nullable = false)
	private String empDept;
	
	@Column(nullable = false)
	private String empName;
	
	@Column(nullable = false)
	private String empBirth;
	
	@Column(nullable = false, unique = true)
	private String empPhone;
	
	@Column(nullable = false)
	private long empSalary;
	
	@Column(nullable = false)
	private String empRole;
	
	@Column(nullable = false)
	private String empStatus;
	
	@CreationTimestamp
	private LocalDateTime empHireDate;
	
	@CollectionTable(name = "employeeRole")
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Set<EmployeeRole> employeeRoles = new HashSet<>();
	
	public EmployeeEntity addRoleToEmployee(EmployeeRole employeeRole) {
		employeeRoles.add(employeeRole);
		
		return this;
	}
	
	public PhoneInfo toPhoneInfo() {
		return PhoneInfo.builder()
				.deptName(empDept)
				.memberName(empName)
				.empRole(empRole)
				.phone(empPhone)
				.build();
	}
	
	public DeptInfo toDeptInfo() {
		return DeptInfo.builder()
				.deptName(empDept)
				.memberName(empName)
				.build();
	}
}