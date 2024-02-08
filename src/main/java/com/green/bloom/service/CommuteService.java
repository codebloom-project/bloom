package com.green.bloom.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.CommuteDTO;

public interface CommuteService {

	void gtwSave(CommuteDTO dto, Authentication user);

	void gowUpdate(Authentication user);
	
	public void gtwCheck(Authentication user, Model model);

}
