package com.green.bloom.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommuteEntityRepository extends JpaRepository<CommuteEntity, Long>{

	List<CommuteEntity> findByEmployee(EmployeeEntity en);

	List<CommuteEntity> findByEmployee_empNo(long empNo);
	
	//MariaDB에서는 DATE() 함수를 사용하여 날짜의 시간 부분을 제거할 수 있습니다. 따라서 MariaDB에서 trunc()와 유사한 효과를 얻으려면 다음과 같이 JPQL을 작성할 수 있습니다:
	@Query("SELECT ce FROM CommuteEntity ce WHERE ce.employee.empNo = :empNo AND DATE(ce.gtwTime) = DATE(:targetDate)")
	Optional<CommuteEntity> findByEmployee_empNoAndGtwTime(@Param("empNo")long empNo,@Param("targetDate") LocalDateTime targetDate);

}
