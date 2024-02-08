package com.green.bloom.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.CommuteDTO;
import com.green.bloom.domain.entity.CommuteEntity;
import com.green.bloom.domain.entity.CommuteEntityRepository;
import com.green.bloom.domain.entity.EmployeeEntityRepository;
import com.green.bloom.security.EmployeeDetails;
import com.green.bloom.service.CommuteService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommuteServiceProcess implements CommuteService{
	
	@Autowired
	CommuteEntityRepository commuteRepo;
	@Autowired
	EmployeeEntityRepository empRepo;
	@Override
	public void gtwSave(CommuteDTO dto, Authentication user) {
		if(user!=null) {
			EmployeeDetails emp=(EmployeeDetails)user.getPrincipal();
			commuteRepo.save(dto.toEntity(empRepo.findByEmpNo(emp.getEmpNo())));
		}
	}
	@Transactional
	@Override
	public void gowUpdate(Authentication user) {
		if(user!=null) {
			EmployeeDetails emp=(EmployeeDetails)user.getPrincipal();
			//empRepo.findById(emp.getEmpNo());
			LocalDateTime.now();
			//commuteRepo.findByEmployee(empRepo.findById(emp.getEmpNo()).orElseThrow()).map(e->e.update(LocalDateTime.now())).orElseThrow();
			commuteRepo.findByEmployee_empNoAndGtwTime(emp.getEmpNo(),LocalDateTime.now()).map(e->e.update(LocalDateTime.now()));
		}
	}
	
	@Override
	public void gtwCheck(Authentication user, Model model) {
		if(user!=null) {
			long EmpNo=((EmployeeDetails)user.getPrincipal()).getEmpNo();
			commuteRepo.findByEmployee_empNoAndGtwTime(EmpNo, LocalDateTime.now())
						.ifPresentOrElse(e->{
							model.addAttribute("isGtw", true);
							if(e.getGowTime()!=null) {
								model.addAttribute("isGow", true);
							}else {
								model.addAttribute("isGow", false);
							}
						}, ()->{
							model.addAttribute("isGtw", false);
							model.addAttribute("isGow", false);
						});
		}
		
	}
	
	
	

}
