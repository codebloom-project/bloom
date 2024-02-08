package com.green.bloom.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.dto.DriveDTO;
import com.green.bloom.domain.entity.DriveEntity;
import com.green.bloom.service.impl.PageResultDTO;

public interface DriveService {
	
	void save(DriveDTO drivedto);

	void drivelist(Model model);

	PageResultDTO<DriveDTO, DriveEntity> getList(PageRequestDTO pageRequestDTO);
	
	

}
