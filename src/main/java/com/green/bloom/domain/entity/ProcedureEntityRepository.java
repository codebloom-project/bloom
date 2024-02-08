package com.green.bloom.domain.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProcedureEntityRepository extends JpaRepository<ProcedureEntity, Long> {

	ProcedureEntity findByProName(String proName);
	
	Optional<ProcedureEntity> findFirstByProName(String proName);
	
	List<ProcedureEntity> findAll();

	Optional<ProcedureEntity> findByProNo(long proNo);
	
	@Query("SELECT p FROM ProcedureEntity p " +
		       "JOIN p.employeeEntity e " +
		       "WHERE LOWER(p.proName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.proClass) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.proType) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(e.empName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.proStart) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.proEnd) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<ProcedureEntity> findByContainingIgnoreCase(@Param("search") String keyword, Pageable pageable);

	List<ProcedureEntity> findTop5ByOrderByProNoDesc();


}
