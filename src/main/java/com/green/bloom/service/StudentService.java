package com.green.bloom.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.bloom.domain.dto.StudentDTO;
import com.green.bloom.domain.entity.StudentEntity;

public interface StudentService {
	
	List<String> getProcedureNames();
	
	void saveStudent(StudentDTO dto);

	void deleteStudents(List<String> studentIds);

	List<StudentEntity> getAllStudents(Model model);

	void listStudent(int page, Model model, String keyword);

}