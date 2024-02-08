package com.green.bloom.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.bloom.domain.dto.LessonSaveDTO;
import com.green.bloom.domain.dto.LessonUpdateDTO;
import com.green.bloom.domain.dto.SchedulePaySaveDTO;
import com.green.bloom.domain.dto.SchedulePayUpdateDTO;
import com.green.bloom.security.EmployeeDetails;
import com.green.bloom.service.LessonService;
import com.green.bloom.service.SchedulePayService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class PayDocumentController {
	
	private final LessonService lessonService;
	private final SchedulePayService schedulePayService;
	
	@GetMapping("/addSceDoc")
	public String addScheduleDocument() {
		return "payDocument/addScheduleDocument.html";
	}
	@GetMapping("/addLesDoc")
	public String addLessonDocument() {
		return "payDocument/addLessonDocument.html";
	}
	@GetMapping("/schedule-doc")
	public String scheduleDocumentList(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			Model model) {
		schedulePayService.listProcess(page, model);
		return "payDocument/schedule-document.html";
	}
	@GetMapping("/schedule-doc/{empNo}")
	public String scheduleDocumentMyList(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			@PathVariable(name="empNo") long empNo,
			Model model) {
		schedulePayService.listMyProcess(page, model, empNo);
		return "payDocument/schedule-document.html";
	}
	@GetMapping("/all-schedule-doc")
	public String allScheduleDoc(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			Model model) {
		schedulePayService.listProcess(page, model);
		return "payDocument/all-schedule-doc.html";
	}
	@GetMapping("/lesson-doc")
	public String lessonDocumentList(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			Model model) {
		lessonService.listProcess(page, model);
		return "payDocument/lesson-document.html";
	}
	@GetMapping("/lesson-doc/{empNo}")
	public String lessonDocumentMyList(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			@PathVariable(name="empNo") long empNo,
			Model model) {
		lessonService.listMyProcess(page, model, empNo);
		return "payDocument/lesson-document.html";
	}
	@GetMapping("/all-lesson-doc")
	public String allLessonDoc(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="pea", defaultValue = "10") int pea,
			Model model) {
		lessonService.listProcess(page, model);
		return "payDocument/all-lesson-doc.html";
	}
	@PostMapping("/add-lesson")
	public String save(LessonSaveDTO dto, Authentication auth) {
		lessonService.saveProcess(dto, auth);
		EmployeeDetails emp=(EmployeeDetails)auth.getPrincipal();
		return "redirect:/lesson-doc/"+emp.getEmpNo();
		//classpath:/templates/board/list.html
	}
	@PostMapping("/add-schedule")
	public String save(SchedulePaySaveDTO dto, Authentication auth) {
		schedulePayService.saveProcess(dto, auth);
		EmployeeDetails emp=(EmployeeDetails)auth.getPrincipal();
		return "redirect:/schedule-doc/"+emp.getEmpNo();
		//classpath:/templates/board/list.html
	}
	@PutMapping("all-schedule-doc/{sdocNo}")
	public String updateScheduleDoc(@PathVariable(name="sdocNo") long sdocNo, SchedulePayUpdateDTO dto) {
		schedulePayService.updateProcess(dto);
		
		return "redirect:/all-schedule-doc";
	}
	@PutMapping("all-lesson-doc/{lessonNo}")
	public String updateLessonDoc(@PathVariable(name="lessonNo") long lessonNo, LessonUpdateDTO dto) {
		lessonService.updateProcess(dto);
		
		return "redirect:/all-lesson-doc";
	}

}
