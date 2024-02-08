package com.green.bloom.domain.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, Long> {
	
	@Query("SELECT s FROM StudentEntity s " +
		       "JOIN s.procedureEntity p " +
		       "WHERE LOWER(s.stuName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(s.stuPhone) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(s.stuBirth) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.proName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(s.stuSitu) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<StudentEntity> findByStuContainingIgnoreCase(@Param("search") String keyword, Pageable pageable);

}
