package com.green.bloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VotingController {

	@GetMapping("/voting")
	public String voting() {
		return "voting/voting";
	}
}
