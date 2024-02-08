package com.green.bloom.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.bloom.domain.dto.ProcedureDTO;
import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.ProcedureEntity;

public interface ProcedureService {

	List<String> getEmployeeNames();

	void saveProcedure(ProcedureDTO dto);
	
	List<String> getAllProcedureNames();

	List<ProcedureEntity> getAllProcedures(Model model);

	void listProcess(int page, Model model, String keyword);

	String convertSearchKeyword(String keyword);

	List<ProcedureEntity> getTop5Procedures();

}
