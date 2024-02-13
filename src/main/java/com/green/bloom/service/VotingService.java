package com.green.bloom.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.bloom.domain.dto.VotingDTO;
import com.green.bloom.domain.entity.VotingEntity;

public interface VotingService {

	void save(VotingDTO votingDTO);

//	List<String> getList();

	void votingList(Model model);

	VotingEntity getVotingById(long id);

	void votingDetail(long id, Model model);

}
