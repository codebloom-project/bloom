package com.green.bloom.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.green.bloom.domain.dto.EmployeeDTO;
import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.EmployeeEntityRepository;
import com.green.bloom.mybatis.mapper.EmployeeMapper;
import com.green.bloom.naverapi.NaverWorkplaceAPIService;
import com.green.bloom.security.EmployeeRole;
import com.green.bloom.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceProcess implements EmployeeService {
	
	private final NaverWorkplaceAPIService naverWorkplaceAPIService;
	
	
	private final EmployeeEntityRepository employeeEntityRepository;
	private final EmployeeMapper employeeMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void employeeRegistration(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity=employeeEntityRepository.save(employeeDTO.toEmployeeEntity(passwordEncoder).addRoleToEmployee(EmployeeRole.SUPERVISOR));
		
		//POST https://workplace.apigw.ntruss.com/organization/apigw/v2/company/{companyId}/employee/{externalKey}
		
		naverWorkplaceAPIService.employeesRegistration(employeeEntity);
	}

	@Override
	public boolean existsByEmpUsername(String empUsername) {
		return employeeEntityRepository.existsByEmpUsername(empUsername);
	}

	@Override
	public void employeeList(Model model) {
		List<EmployeeDTO> employeeList = employeeMapper.findAll();
		
		model.addAttribute("employeeList", employeeList);
	}

	@Override
	public ModelAndView employeeDetails(long empNo) {
		return new ModelAndView("/employee/employeeDetails")
				.addObject("employeeDetails", employeeMapper.employeeDetails(empNo));
	}

	@Override
	public void employeeUpdate(EmployeeDTO employeeDTO) {
		employeeMapper.employeeUpdate(employeeDTO);
	}
}