package com.green.bloom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.bloom.domain.dto.ScheduleDTO;
import com.green.bloom.domain.dto.VotingDTO;
import com.green.bloom.domain.entity.VotingEntityRepository;
import com.green.bloom.service.VotingService;

@Controller
public class VotingController {

	@Autowired
	VotingEntityRepository votingRepo;
	@Autowired
	VotingService votingService;
	
	@GetMapping("/voting")
	public String voting() {
		return "voting/voting";
	}
	
	@PostMapping("/voting")
	public String voting(VotingDTO votingDTO) {
		votingService.save(votingDTO);
		return "voting/voting";
	}
	
	@GetMapping("/voting-list")
	public List<VotingDTO> votingList() {
		return votingService.getList();
	}
}
