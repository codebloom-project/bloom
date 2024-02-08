package com.green.bloom.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.green.bloom.controller.SchedulePageRequestDTO;
import com.green.bloom.domain.dto.ScheduleDTO;
import com.green.bloom.domain.entity.ScheduleEntity;
import com.green.bloom.service.impl.PageResultDTO;

public interface ScheduleService {

	List<ScheduleDTO> getAllSchedules();

	void edit(long no, ScheduleDTO scheduleDTO);

	void delete(long no);

	void scheduleList(Model model);

	PageResultDTO<ScheduleDTO, ScheduleEntity> getList(SchedulePageRequestDTO SpageRequestDTO);

	void save(ScheduleDTO scheduleDTO);




}
