package com.green.bloom.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.bloom.domain.dto.EmployeeDTO;

@Mapper
public interface EmployeeMapper {

	List<EmployeeDTO> findAll();

	EmployeeDTO employeeDetails(long empNo);

	void employeeUpdate(EmployeeDTO employeeDTO);
}