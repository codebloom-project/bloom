package com.green.bloom.service.impl.notice;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.bloom.domain.NoticeEntity.NoticeDTO;
import com.green.bloom.domain.NoticeEntity.NoticeDTO.NoticeDTOBuilder;
import com.green.bloom.service.impl.PageResultDTO;
import com.green.bloom.domain.NoticeEntity.NoticeUpdateDTO;

import jakarta.transaction.Transactional;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.NoticeEntity.Notice;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepository;

public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {
		
		Pageable pageable = requestDTO.getPageable(10 ,Sort.by("id").descending());
		
		Page<Notice> result = noticeRepository.findAll(pageable);
		
		Function<Notice, NoticeDTO> fn = entity -> entity.toDto();
		
		return new PageResultDTO<>(result, fn);
	}
	
	
	public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
	
	
	
	//공지사항 저장
	public Notice save(NoticeDTO dto) {
		return noticeRepository.save(dto.toEntity());
	}

	// 전체공지사항조회
	public List<Notice> findAll() {
		return noticeRepository.findAll();
	}

	public Optional<Notice> findbyId(Long id) {
		return noticeRepository.findById(id);
	}

	public void noticeList(Model model) {
		Sort sort=Sort.by(Direction.ASC, "id");
		List<NoticeDTO> list = noticeRepository.findAll(sort).stream()
				.map(Notice::toDto)
				.collect(Collectors.toList());
		model.addAttribute("noticelist", list);
	}

	public Notice updateNotice(Long id, String title, String content) {
		Notice existingNotice = noticeRepository.findById(id).orElseThrow(null);

        if (existingNotice != null) {
            existingNotice.setTitle(title);
            existingNotice.setContent(content);
            return noticeRepository.save(existingNotice);
        }
		return existingNotice;

    }

	// 공지사항 삭제하는 메서드
	public void delete(long id) {
		noticeRepository.deleteById(id);
	}

	@Transactional
	public void updateNotice(long id, NoticeUpdateDTO dto) {
		log.debug("---> 수정전 <----");
		noticeRepository.findById(id).orElseThrow().updateTitleAndContent(dto);
		log.debug("---> 수정완료 <----");
	}



	public void save(NoticeDTOBuilder content) {
		// TODO Auto-generated method stub
		
	}

}
