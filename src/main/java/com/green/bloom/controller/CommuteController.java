package com.green.bloom.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.green.bloom.domain.NoticeEntity.Notice;
import com.green.bloom.domain.dto.CommuteDTO;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.service.CommuteService;
import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.impl.CommuteServiceProcess;
import com.green.bloom.service.impl.notice.NoticeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class CommuteController {
	
	@Autowired
	private final CommuteService commuteService;
	
	
	@GetMapping("/weather")
	public String weather() {
		//출근버튼이 눌러졌는지
		
		return"weather/weather-view.html";
	}
	
	@PostMapping("/today/gtw")
	public String gtw(CommuteDTO dto, Authentication user) {
		commuteService.gtwSave(dto, user);
		return "redirect:/";
	}
	@PutMapping("/today/gow")
	public String gow(Authentication user) {
		commuteService.gowUpdate(user);
		return "redirect:/";
	}
	
	@GetMapping("/commute")
	public String commute() {
		return "commute/commute.html";
	}

}
