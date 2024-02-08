package com.green.bloom.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.controller.SchedulePageRequestDTO;
import com.green.bloom.domain.dto.ScheduleDTO;
import com.green.bloom.domain.entity.ScheduleEntity;
import com.green.bloom.domain.entity.ScheduleEntityRepository;
import com.green.bloom.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceProcess implements ScheduleService{
	
	@Autowired
	ScheduleEntityRepository ScheduleRepo;

	@Override
	public void save(ScheduleDTO scheduledto) {
		ScheduleRepo.save(scheduledto.toEntity());
	}

	@Override
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = ScheduleRepo.findAll();
        return schedules.stream()
                .map(ScheduleEntity::toDTO)
                .collect(Collectors.toList());
    }

	@Override
	public void edit(long no, ScheduleDTO scheduleDTO) {
	    ScheduleEntity existingSchedule = ScheduleRepo.findById(no)
	        .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));
	    
	    existingSchedule.setTitle(scheduleDTO.getTitle());
	    existingSchedule.setContent(scheduleDTO.getContent());
	    existingSchedule.setStart(scheduleDTO.getStart());
	    existingSchedule.setEnd(scheduleDTO.getEnd());
	    
	    ScheduleRepo.save(existingSchedule);
	}

	@Override
	public void delete(long no) {
	    ScheduleEntity schedule = ScheduleRepo.findById(no)
	        .orElseThrow();
	    ScheduleRepo.delete(schedule);
	}

	@Override
	public void scheduleList(Model model) {
	    Sort sort = Sort.by(Sort.Order.asc("start"));
	    model.addAttribute("list", ScheduleRepo.findAll(sort).stream()
	            .map(ScheduleEntity::toDTO)
	            .collect(Collectors.toList()));
	}


	@Override
	public PageResultDTO<ScheduleDTO, ScheduleEntity> getList(SchedulePageRequestDTO SpageRequestDTO) {
		Pageable pageable = SpageRequestDTO.getPageable(6, Sort.by("start"));
		Page<ScheduleEntity> result = ScheduleRepo.findAll(pageable);
		Function<ScheduleEntity, ScheduleDTO> fn = entity -> entity.toDTO();
		return new PageResultDTO<>(result, fn);
	}


}
