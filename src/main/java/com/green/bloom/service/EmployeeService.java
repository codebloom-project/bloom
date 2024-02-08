package com.green.bloom.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.green.bloom.domain.dto.EmployeeDTO;

public interface EmployeeService {
	
	void employeeRegistration(EmployeeDTO employeeDTO);
	
	boolean existsByEmpUsername(String empUsername);

	void employeeList(Model model);

	ModelAndView employeeDetails(long empNo);

	void employeeUpdate(EmployeeDTO employeeDTO);
}