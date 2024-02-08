package com.green.bloom.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.LessonSaveDTO;
import com.green.bloom.domain.dto.LessonUpdateDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;

public interface LessonService {

	void saveProcess(LessonSaveDTO dto, Authentication auth);

	void listProcess(int page, Model model);

	void updateProcess(LessonUpdateDTO dto);
	
	void listMyProcess(int page, Model model, long empNo);


}
