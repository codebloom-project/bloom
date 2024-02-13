package com.green.bloom.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.bloom.domain.dto.VotingDTO;

public interface VotingService {

	void save(VotingDTO votingDTO);

	List<VotingDTO> getList();

}
