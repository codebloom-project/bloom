package com.green.bloom.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.LessonDTO;
import com.green.bloom.domain.dto.LessonSaveDTO;
import com.green.bloom.domain.dto.LessonUpdateDTO;
import com.green.bloom.domain.dto.SchedulePayDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;
import com.green.bloom.mybatis.mapper.LessonMapper;
import com.green.bloom.security.EmployeeDetails;
import com.green.bloom.service.LessonService;
import com.green.bloom.utils.PageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LessonServiceProcess implements LessonService{
	
	private final LessonMapper lessonMapper;
	
	@Override
	public void saveProcess(LessonSaveDTO dto, Authentication auth) {
		// board테이블에 dto의 내용을 저장
		log.debug(dto.toString());
		EmployeeDetails emp=(EmployeeDetails)auth.getPrincipal();
		dto.setEmpNo(emp.getEmpNo());
		lessonMapper.save(dto);
	}
	@Override
	public void listProcess(int page, Model model) {
		int offset=(page-1)*10; // 건너뛰는 개수 1page={offset=0}
		int limit=10; // 한페이지에 표현할 rows수
		//RowBounds pagging=new RowBounds(offset, limit);
		List<LessonDTO> result=lessonMapper.findAllLimit(offset, limit);
		model.addAttribute("lessonList", result);
		
		int rowCount = lessonMapper.countAll();
		PageUtil pageUtil=PageUtil.createPage(rowCount, limit, page, 5);
		model.addAttribute("pu", pageUtil);
		
		
		//model.addAttribute("from", from);
		//model.addAttribute("to", to);
		//model.addAttribute("tot", tot);
		
	}
	@Override
	public void updateProcess(LessonUpdateDTO dto) {
		lessonMapper.updateLessonCurrent(dto);
		
	}
	@Override
	public void listMyProcess(int page, Model model, long empNo) {
		int offset=(page-1)*10; // 건너뛰는 개수 1page={offset=0}
		int limit=10; // 한페이지에 표현할 rows수
		//RowBounds pagging=new RowBounds(offset, limit);
		List<SchedulePayDTO> result=lessonMapper.findLimit(offset, limit, empNo);
		model.addAttribute("lessonList", result);
		
		int rowCount = lessonMapper.count(empNo);
		PageUtil pageUtil=PageUtil.createPage(rowCount, limit, page, 5);
		model.addAttribute("pu", pageUtil);
		
	}

}
