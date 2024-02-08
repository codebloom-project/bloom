package com.green.bloom.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.dto.ProPageRequestDTO;
import com.green.bloom.domain.dto.ProcedureDTO;
import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.EmployeeEntityRepository;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.domain.entity.ProcedureEntityRepository;
import com.green.bloom.service.ProcedureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureServiceProcess implements ProcedureService {

	@Autowired
    private EmployeeEntityRepository employeeEntityRepository;
	
	@Autowired
	private ProcedureEntityRepository procedureEntityRepository;
	
	
    @Override
    public List<String> getEmployeeNames() {
        List<EmployeeEntity> empEntities = employeeEntityRepository.findAll();
        return empEntities.stream()
                .map(EmployeeEntity::getEmpName)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProcedure(ProcedureDTO dto) {
        ProcedureEntity existingProcedure = procedureEntityRepository.findById(dto.getProNo()).orElse(null);

        if (existingProcedure != null) {
            existingProcedure.setProName(dto.getProName());
            existingProcedure.setProClass(dto.getProClass());
            existingProcedure.setProType(dto.getProType());
            existingProcedure.setProStart(dto.getProStart());
            existingProcedure.setProEnd(dto.getProEnd());

            if (dto.getEmpName() != null) {
                // empName을 사용하여 EmployeeEntity를 찾음
                Optional<EmployeeEntity> employeeOptional = Optional.ofNullable(employeeEntityRepository.findByEmpName(dto.getEmpName()));
                
                EmployeeEntity employeeEntity = employeeOptional.orElseThrow(() -> new IllegalArgumentException("Employee not found with empName: " + dto.getEmpName()));

                existingProcedure.setEmployeeEntity(employeeEntity);
            }

            procedureEntityRepository.save(existingProcedure);
        } else {
            // 새로운 ProcedureEntity를 생성하여 저장
            if (dto.getEmpName() != null) {
                // empName을 사용하여 EmployeeEntity를 찾음
                Optional<EmployeeEntity> employeeOptional = Optional.ofNullable(employeeEntityRepository.findByEmpName(dto.getEmpName()));
                
                EmployeeEntity employeeEntity = employeeOptional.orElseThrow(() -> new IllegalArgumentException("Employee not found with empName: " + dto.getEmpName()));

                ProcedureEntity newProcedure = dto.toProcedureEntity(employeeEntity);
                procedureEntityRepository.save(newProcedure);
            } else {
                // empName이 null인 경우에는 EmployeeEntity를 null로 설정
                ProcedureEntity newProcedure = dto.toProcedureEntity(null);
                procedureEntityRepository.save(newProcedure);
            }
        }
    }
	
	public List<String> getAllProcedureNames() {
        List<ProcedureEntity> procedures = procedureEntityRepository.findAll();
        return procedures.stream().map(ProcedureEntity::getProName).collect(Collectors.toList());
    }

	@Override
    public List<ProcedureEntity> getAllProcedures(Model model) {
        return procedureEntityRepository.findAll();
    }

	@Override
    public void listProcess(int page, Model model, String keyword) {
        int limit = 10;
		/* int offset = (page - 1) * limit; */

        // PageRequest를 사용하여 페이징 정보를 생성
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "proNo"));

        // 검색어가 주어진 경우 검색을 수행하고, 그렇지 않으면 전체 리스트를 가져옴
        Page<ProcedureEntity> procedureEntityPage;
        if (keyword != null && !keyword.isEmpty()) {
            procedureEntityPage = procedureEntityRepository.findByContainingIgnoreCase(keyword, pageable);
        } else {
            procedureEntityPage = procedureEntityRepository.findAll(pageable);
        }

        List<ProcedureDTO> result = procedureEntityPage.getContent().stream()
                .map(ProcedureEntity::toProcedureDTO)
                .collect(Collectors.toList());
        model.addAttribute("list", result);

        int rowCount = (int) procedureEntityPage.getTotalElements();
        model.addAttribute("pu", ProPageRequestDTO.create(page, limit, rowCount, 5));
    }
	
	@Override
    public String convertSearchKeyword(String keyword) {
        // 예시: "입시반"을 "ee class"로 변환
        if ("입시반".equalsIgnoreCase(keyword)) {
            return "ee class";
        } else if ("입시".equalsIgnoreCase(keyword)) {
        	return "ee class";
        } else if ("취미반".equalsIgnoreCase(keyword)) {
            return "hobby class";
        } else if ("취미".equalsIgnoreCase(keyword)) {
        	return "hobby class";
        } else if ("기타".equalsIgnoreCase(keyword)) {
            return "guitar";
        } else if ("드럼".equalsIgnoreCase(keyword)) {
            return "drum";
        } else if("피아노".equalsIgnoreCase(keyword)) {
        	return "piano";
        }
        
        return keyword;
    }
	
	@Override
    public List<ProcedureEntity> getTop5Procedures() {
        return procedureEntityRepository.findTop5ByOrderByProNoDesc(); // 최신순으로 정렬
    }
	
}