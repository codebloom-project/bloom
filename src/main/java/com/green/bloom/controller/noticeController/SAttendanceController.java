package com.green.bloom.controller.noticeController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.NoticeEntity.Notice;
import com.green.bloom.domain.NoticeEntity.NoticeDTO;
import com.green.bloom.domain.dto.StudentAttDTO;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.domain.entity.ProcedureEntityRepository;
import com.green.bloom.domain.entity.StudentAtt;
import com.green.bloom.domain.entity.StudentAttRepository;
import com.green.bloom.domain.entity.StudentEntity;
import com.green.bloom.domain.entity.StudentEntityRepository;
import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.impl.ProcedureServiceProcess;
import com.green.bloom.service.impl.StuAttService;
import com.green.bloom.service.impl.StuAttServiceProcess;
import com.green.bloom.service.impl.StudentServiceProcess;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Controller
public class SAttendanceController {

	@Autowired
	private StuAttServiceProcess ssp;
	
	//@Autowired
	//StudentAttRepository sarepository;

	@Autowired
	ProcedureEntityRepository pa;

	@Autowired
	ProcedureService ps;

	@GetMapping("/attlist")
	public String procedure(PageRequestDTO pageRequestDTO, Model model) {
		List<ProcedureEntity> procedures = ps.getAllProcedures(model);
		model.addAttribute("procedures", procedures);
		return "/attendance/attlist";
	}

	//// // 과정상세로 접속
	@GetMapping("/procedure/{proNo}") //// public String
	public String getProcedure(@PathVariable("proNo") Long proNo, Model model) { ////
		ssp.getProcedureProcess(proNo, model);
    return "attendance/adetail";
	}
	
	@GetMapping("stuatt")
	public String stuatt(Model model) {
		return "/attendance/StuAtt";
	}

//	@GetMapping("/by-proNo/{proNo}")
//    public String getStudentsByProNo(@PathVariable Long proNo, Model model) {
//        List<StudentAtt> studentAtt = ssp.getStudentsByCourseName(proNo);
//        model.addAttribute("studentAtt", studentAtt); // students 대신에 studentAtt로 변경
//        return "StuAtt"; // 뷰 이름, 해당 뷰에 결과를 전달하면서 이동
//    }
	
//	@GetMapping("/attlist/{proNo}")
//	public String stuatt(@PathVariable("proNo") Long proNo, Model model) {
//		StudentAtt stuatt= sarepository.findByStudentEntity_proNo(proNo);
//		model.addAttribute("stuatt", stuatt);
//		return "attendance/stuatt";
//		
//	}
}
