package com.green.bloom.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingEntityRepository extends JpaRepository<ScheduleEntity, Long>{

	void save(VotingEntity entity);

}
