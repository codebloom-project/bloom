package com.green.bloom.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.NoticeEntity.Notice;
import com.green.bloom.domain.NoticeEntity.NoticeDTO;
import com.green.bloom.domain.dto.ProcedureDTO;
import com.green.bloom.domain.dto.StudentAttDTO;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.domain.entity.ProcedureEntityRepository;
import com.green.bloom.domain.entity.StudentAtt;
import com.green.bloom.domain.entity.StudentAttRepository;
import com.green.bloom.domain.entity.StudentEntity;
import com.green.bloom.domain.entity.StudentEntityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StuAttServiceProcess implements StuAttService {

	@Autowired
	StudentAttRepository satRepository;
	
	@Autowired
	ProcedureEntityRepository pa;

	public List<StudentAtt> getAllStudents() {
		return satRepository.findAll();
	}

	public StudentAtt save(StudentAttDTO dto) {
		return satRepository.save(dto.toEntity());
	}

	public void getProcedureProcess(Long proNo, Model model) {
		ProcedureEntity procudureid= pa.findById(proNo).orElseThrow();
		LocalDate start=LocalDate.parse(procudureid.getProStart());
		LocalDate end=LocalDate.parse(procudureid.getProEnd());
		
		model.addAttribute("list", getDatesBetween(start, end));
	}
	
	public List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }

	
	// isPresent를 false에서 true로 바꾸기
	public void setPresentTrue(String SANumber, StudentAttDTO updateDTO) {
		StudentAtt studentAtt = satRepository.findById(SANumber)
				.orElseThrow(() -> new EntityNotFoundException("SANumber not found with id: " + SANumber));

		studentAtt.setPresent(updateDTO.isPresent());

		// 변경된 엔터티를 데이터베이스에 저장
		satRepository.save(studentAtt);
	}

	
}
