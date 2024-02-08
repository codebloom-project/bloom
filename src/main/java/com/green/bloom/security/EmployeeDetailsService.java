package com.green.bloom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.green.bloom.domain.entity.EmployeeEntityRepository;

public class EmployeeDetailsService implements UserDetailsService {
	
	@Autowired
	private EmployeeEntityRepository employeeEntityRepository;
	
	@Override
	public UserDetails loadUserByUsername(String empUsername) throws UsernameNotFoundException {
		
		return new EmployeeDetails(employeeEntityRepository.findByEmpUsername(empUsername + "@codebloom.com").orElseThrow());
	}
}