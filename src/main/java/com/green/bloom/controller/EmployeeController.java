package com.green.bloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.bloom.domain.dto.EmployeeDTO;
import com.green.bloom.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@GetMapping("/sign")
	public String signIn(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "exception", required = false) String exception,
			Model model) {
		
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		
		return "sign/sign";
	}
	
	@PostMapping("/sign/signup")
	public String employeeRegistration(EmployeeDTO employeeDTO) {
		employeeService.employeeRegistration(employeeDTO);
		
		return "redirect:/sign";
	}
	
	@PostMapping("/sign/exists-username")
	@ResponseBody
	public boolean checkEmpUsername(@RequestParam(value = "empUsername") String empUsername) {
		return employeeService.existsByEmpUsername(empUsername);
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "sign/accessDenied";
	}
	
	@GetMapping("/employee")
	public String employeeDirectory(Model model) {
		employeeService.employeeList(model);
		
		return "employee/employeeDirectory";
	}
	
	@GetMapping("/employee/details/{empNo}")
	@ResponseBody
	public ModelAndView employeeDetails(@PathVariable(name = "empNo") long empNo) {
		return employeeService.employeeDetails(empNo);
	}
	
	@PutMapping("/employee/details/{empNo}")
	public String employeeUpdate(EmployeeDTO employeeDTO) {
		employeeService.employeeUpdate(employeeDTO);
		System.out.println(employeeDTO.getEmpHireDate());
		return "redirect:/employee";
	}
	
	@GetMapping("/email")
	public String employeeEmail() {
		
		
		return "employee/employeeEmail";
	}
}
