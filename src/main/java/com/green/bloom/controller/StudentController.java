package com.green.bloom.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.bloom.domain.dto.StudentDTO;
import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.StudentService;

@Controller
public class StudentController {
		
	@Autowired
    private StudentService studentService;
	
	@Autowired
    private ProcedureService procedureService;
	
	//학생관리 페이지 이동
	@GetMapping("/student")
	public String student(@RequestParam(name="page",defaultValue = "1") int page, 
						  @RequestParam(name = "search", required = false) String keyword,
						  Model model) {
		
		// 학생 목록 조회
		studentService.listStudent(page, model, keyword);
		
		// 프로시저 목록 조회
        List<String> procedureNames = procedureService.getAllProcedureNames();
        model.addAttribute("procedureNames", procedureNames);
		
		// 검색어를 모델에 추가
		model.addAttribute("keyword", keyword);
		
		return "/student/student";
	}
	
	//학생등록 페이지 이동
	@GetMapping("/student_add")
	public String student_add(Model model) {
		List<String> procedureNames = studentService.getProcedureNames();
        model.addAttribute("procedureNames", procedureNames);
		return "/student/student_add";
	}

	//학생 등록하기
	@PostMapping("/student_add")
	public String student_add(StudentDTO dto) throws Exception {
		studentService.saveStudent(dto);
		return "redirect:/student";
	}
	
	//수정버튼
	@PostMapping("/studentSaveChanges")
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveChanges(@RequestBody StudentDTO studentDTO) {
	    try {
	    	studentService.saveStudent(studentDTO);
	        return ResponseEntity.ok(Map.of("message", "저장 완료"));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "저장 실패"));
	    }
	}
	
	// 선택된 학생들 삭제
    @PostMapping("/deleteStudents")
    public String deleteStudents(@RequestParam("selectedStudents") String selectedStudents) {
        List<String> studentIds = Arrays.asList(selectedStudents.split(","));
        studentService.deleteStudents(studentIds);
        return "redirect:/student";
    }
	
}
