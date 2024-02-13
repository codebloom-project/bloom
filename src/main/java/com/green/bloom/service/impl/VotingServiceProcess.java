package com.green.bloom.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.ScheduleDTO;
import com.green.bloom.domain.dto.VotingDTO;
import com.green.bloom.domain.entity.ScheduleEntity;
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
	public List<VotingDTO> getList() {
        List<VotingEntity> votings = votingRepo.findAll();
        return votings.stream()
                .map(VotingEntity::toDTO)
                .collect(Collectors.toList());
    }
	
}
