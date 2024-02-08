package com.green.bloom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.bloom.domain.NoticeEntity.Notice;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.service.CommuteService;
import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.impl.notice.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	@Autowired
	private final CommuteService commuteService;
	@Autowired
    private ProcedureService procedureService;
	@Autowired
	public NoticeService noticeService;
	
	@GetMapping("/")
	public String index(Authentication user,Model model) {
		//출근버튼이 눌러졌는지
		List<ProcedureEntity> top5Procedures = procedureService.getTop5Procedures();
        model.addAttribute("top5Procedures", top5Procedures);
        List<Notice> listsection = noticeService.findAll();
		model.addAttribute("listsection", listsection);
		commuteService.gtwCheck(user,model);
		return"index";
	}
}
