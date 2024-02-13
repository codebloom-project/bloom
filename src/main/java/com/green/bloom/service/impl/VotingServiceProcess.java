package com.green.bloom.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.VotingDTO;
import com.green.bloom.domain.entity.VotingEntity;
import com.green.bloom.domain.entity.VotingEntityRepository;
import com.green.bloom.service.VotingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotingServiceProcess implements VotingService{
	
	@Autowired
	VotingEntityRepository votingRepo;
	
	@Override
	public void save(VotingDTO votingDTO) {
		votingRepo.save(votingDTO.toEntity());
	}

	@Override
	public void votingList(Model model) {
		model.addAttribute("list", votingRepo.findAll().stream()
				.map(VotingEntity::toDTO)
				.collect(Collectors.toList()));
	}

	@Override
    public VotingEntity getVotingById(long id) {
        Optional<VotingEntity> optionalVoting = votingRepo.findById(id);
        return optionalVoting.orElseThrow();
    }

	@Override
	public void votingDetail(long id, Model model) {
		model.addAttribute("voting", votingRepo.findById(id)
				.map(VotingEntity::toDTO)
				.orElseThrow()
				);
		
	}

	
}
