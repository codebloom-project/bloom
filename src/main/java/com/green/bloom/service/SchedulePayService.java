package com.green.bloom.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.SchedulePaySaveDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;

public interface SchedulePayService {

	void saveProcess(SchedulePaySaveDTO dto, Authentication auth);
	
	void listProcess(int page, Model model);

	void updateProcess(SchedulePayUpdateDTO dto);

	void listMyProcess(int page, Model model, long empNo);

}
