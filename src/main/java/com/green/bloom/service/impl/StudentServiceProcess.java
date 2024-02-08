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

import com.green.bloom.domain.dto.StuPageRequestDTO;
import com.green.bloom.domain.dto.StudentDTO;
import com.green.bloom.domain.entity.ProcedureEntity;
import com.green.bloom.domain.entity.ProcedureEntityRepository;
import com.green.bloom.domain.entity.StudentEntity;
import com.green.bloom.domain.entity.StudentEntityRepository;
import com.green.bloom.service.ProcedureService;
import com.green.bloom.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceProcess implements StudentService {

	@Autowired
	private StudentEntityRepository studentEntityRepository;
	
	@Autowired
	private ProcedureEntityRepository procedureEntityRepository;
	
	@Autowired
    private ProcedureService procedureService;
	
	@Override
    public void saveStudent(StudentDTO dto) {
		StudentEntity existingStudent = studentEntityRepository.findById(dto.getStuNo()).orElse(null);
        
		 if (existingStudent != null) {
		        existingStudent.setStuName(dto.getStuName());
		        existingStudent.setStuBirth(dto.getStuBirth());
		        existingStudent.setStuPhone(dto.getStuPhone());
		        existingStudent.setStuSitu(dto.getStuSitu());
		        
		        if (dto.getProName() != null) {
		        	Optional<ProcedureEntity> procedureOptional = procedureEntityRepository.findFirstByProName(dto.getProName());

		        	ProcedureEntity procedureEntity = procedureOptional.orElseThrow(() -> new IllegalArgumentException("Procedure not found with proName: " + dto.getProName()));

	                existingStudent.setProcedureEntity(procedureEntity);
	            }
		        
		        studentEntityRepository.save(existingStudent);
		} else {
            if (dto.getProName() != null) {
            	Optional<ProcedureEntity> procedureOptional = procedureEntityRepository.findFirstByProName(dto.getProName());

            	ProcedureEntity procedureEntity = procedureOptional.orElseThrow(() -> new IllegalArgumentException("Procedure not found with proName: " + dto.getProName()));

                StudentEntity newStudent = dto.toStudentEntity(procedureEntity);
                
                // 기본값 설정
                newStudent.setStuSitu("재원중");
                
                studentEntityRepository.save(newStudent);
            } else {
                StudentEntity newStudent = dto.toStudentEntity(null);
                
                // 기본값 설정
                newStudent.setStuSitu("재원중");
                
                studentEntityRepository.save(newStudent);
            }
        }
    }
	
	

	@Override
	public List<String> getProcedureNames() {
		return procedureService.getAllProcedureNames();
	}
	
	@Override
    public void deleteStudents(List<String> studentIds) {
        for (String studentId : studentIds) {
            try {
                // 대괄호 제거 후 숫자로 변환
                String cleanedId = studentId.replaceAll("[^0-9]", "");
                if (!cleanedId.isEmpty()) {
                    Long id = Long.valueOf(cleanedId);
                    System.out.println("Deleting student with ID: " + id);
                    
                    Optional<StudentEntity> optionalStudent = studentEntityRepository.findById(id);
                    optionalStudent.ifPresent(student -> {
                        // 학생의 ProcedureEntity와의 연결 해제
                        student.detachProcedureEntity();
                        // 학생 삭제
                        studentEntityRepository.delete(student);
                    });
                }
            } catch (NumberFormatException e) {
                // 예외 처리: 유효한 숫자가 아닌 경우
                e.printStackTrace();
            }
        }
    }
	
	@Override
	public List<StudentEntity> getAllStudents(Model model) {
		return studentEntityRepository.findAll();
	}
	
	@Override
    public void listStudent(int page, Model model, String keyword) {
        int limit = 10;
		/* int offset = (page - 1) * limit; */

        // PageRequest를 사용하여 페이징 정보를 생성
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "stuNo"));

        // Spring Data JPA에서 페이징 처리를 위한 메서드를 사용
        Page<StudentEntity> studentEntityPage;
        if (keyword != null && !keyword.isEmpty()) {
        	studentEntityPage = studentEntityRepository.findByStuContainingIgnoreCase(keyword, pageable);
        } else {
        	studentEntityPage = studentEntityRepository.findAll(pageable);
        }

        List<StudentDTO> result = studentEntityPage.getContent().stream()
                .map(StudentEntity::toStudentDTO)
                .collect(Collectors.toList());
        model.addAttribute("list", result);

        int rowCount = (int) studentEntityPage.getTotalElements();
        model.addAttribute("pu", StuPageRequestDTO.create(page, limit, rowCount, 5));
    }
	
}
