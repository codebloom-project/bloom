package com.green.bloom.controller;

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

import com.green.bloom.domain.dto.ProcedureDTO;
import com.green.bloom.service.EmployeeService;
import com.green.bloom.service.ProcedureService;

@Controller
public class ProcedureController {
	
	@Autowired
    private ProcedureService procedureService;
	
	//과목관리 페이지 이동
	@GetMapping("/procedure")
    public String procedure(@RequestParam(name="page",defaultValue = "1") int page, 
    						@RequestParam(name = "search", required = false) String keyword,
    						Model model) {
		// 검색어 변환
        String convertedKeyword = procedureService.convertSearchKeyword(keyword);
		
        // 과목 목록 조회
		procedureService.listProcess(page, model, convertedKeyword);
		
		// 프로시저 목록 조회
        List<String> employeeNames = procedureService.getEmployeeNames();
        model.addAttribute("employeeNames", employeeNames);
		
		// 검색어를 모델에 추가
		model.addAttribute("keyword", keyword);
		
        return "procedure/procedure";
    }
	
	//과목등록 페이지 이동
	@GetMapping("/procedure_add")
	public String procedure_add(Model model) {
		List<String> employeeNames = procedureService.getEmployeeNames();
        model.addAttribute("employeeNames", employeeNames);
		return "/procedure/procedure_add";
	}
	
	//과목 등록하기
	@PostMapping("/procedure_add")
	public String procedure_add(ProcedureDTO dto) throws Exception {
		procedureService.saveProcedure(dto);
		return "redirect:/procedure";
	}
	
	//수정버튼
	@PostMapping("/saveChanges")
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveChanges(@RequestBody ProcedureDTO procedureDTO) {
	    try {
	        procedureService.saveProcedure(procedureDTO);
	        return ResponseEntity.ok(Map.of("message", "저장 완료"));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "저장 실패"));
	    }
	}

}
