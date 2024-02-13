package com.green.bloom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.bloom.domain.dto.VotingDTO;
import com.green.bloom.domain.entity.VotingEntity;
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
	public String votingList(Model model) {
		votingService.votingList(model);
		return "voting/voting-list";
	}
	
	@GetMapping("/voting/{id}")
	public String votingDetail(@PathVariable("id") long id, Model model) {
//	    VotingEntity voting = votingService.getVotingById(id);
//	    model.addAttribute("voting", voting);
		votingService.votingDetail(id, model);
	    return "voting/voting-detail";
	}

}
