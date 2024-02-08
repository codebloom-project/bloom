package com.green.bloom.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long>{

	List<ScheduleEntity> findByCategori(boolean categori);

	
}
