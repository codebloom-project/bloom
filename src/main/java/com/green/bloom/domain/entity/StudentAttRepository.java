package com.green.bloom.domain.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAttRepository extends JpaRepository<StudentAtt, String>{

	//StudentAtt findByStudentEntity_proNo(Long proNo);

	// List<StudentAtt> findByStudentEntity_proNo(Long proNo);


}
