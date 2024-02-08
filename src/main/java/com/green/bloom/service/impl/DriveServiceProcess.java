package com.green.bloom.service.impl;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.dto.DriveDTO;
import com.green.bloom.domain.entity.DriveEntity;
import com.green.bloom.domain.entity.DriveEntityRepository;
import com.green.bloom.service.DriveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DriveServiceProcess implements DriveService{
	
	@Autowired
	DriveEntityRepository driveRepo;
	
	@Override
	public void save(DriveDTO drivedto) {
		driveRepo.save(drivedto.toEntity());
	}
	@Override
	public void drivelist(Model model) {
		Sort sort=Sort.by(Direction.DESC, "no");
		model.addAttribute("list", driveRepo.findAll(sort).stream()
				.map(DriveEntity::toDriveDTO)
				.collect(Collectors.toList())
		);
	}
	@Override
	public PageResultDTO<DriveDTO, DriveEntity> getList(PageRequestDTO requestDTO) {
		
		Pageable pageable = requestDTO.getPageable(10 ,Sort.by("no").descending());
		
		Page<DriveEntity> result = driveRepo.findAll(pageable);
		
		Function<DriveEntity, DriveDTO> fn = entity -> entity.toDriveDTO();
		
		return new PageResultDTO<>(result, fn);
	}
	

}
