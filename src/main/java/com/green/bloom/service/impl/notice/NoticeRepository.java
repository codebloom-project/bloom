package com.green.bloom.service.impl.notice;


import org.springframework.data.jpa.repository.JpaRepository;

import com.green.bloom.domain.NoticeEntity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{



}
