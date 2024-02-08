package com.green.bloom.controller.noticeController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.bloom.controller.PageRequestDTO;
import com.green.bloom.domain.NoticeEntity.Notice;
import com.green.bloom.domain.NoticeEntity.NoticeDTO;
import com.green.bloom.domain.NoticeEntity.NoticeUpdateDTO;
import com.green.bloom.service.impl.notice.NoticeRepository;
import com.green.bloom.service.impl.notice.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {

	@Autowired
	public NoticeService noticeService;

	@Autowired
	NoticeRepository noticeRepository;

//	@PostMapping("/noticewrite")
//	public String save(NoticeDTO dto) {
//		noticeService.save(dto);
//		return "redirect:";
//	}
	

	@GetMapping("/noticemanage")
	public String noticeList(PageRequestDTO pageRequestDTO, Model model) {
		model.addAttribute("result", noticeService.getList(pageRequestDTO));
		noticeService.noticeList(model);
		return "notice/noticemanage";
	}

	@GetMapping("/preview")
	public String preview() {
		return "notice/preview";
	}

	/////////// 요 아래는 건들 것 없음//////////////

	// 공지사항 등록 누르면 등록페이지로 이동
	@GetMapping("/notice/noticewrite")
	public String noticewrite() {
		return "notice/noticewrite";
	}

	// 공지사항 저장 후 공지사항 등록 페이지로 가기
	@PostMapping("/notice/noticewrite")
	public String noticesave(NoticeDTO dto) {
		noticeService.save(dto);
		return "redirect:noticewrite";
	}

	// 상세공지사항으로 접속
	@GetMapping("/notice/{id}")
	public String getnoticeid(@PathVariable("id") Long id, Model model) {
		Notice noticeid = noticeService.findbyId(id).orElseThrow();
		model.addAttribute("dto", noticeid);
		return "notice/detail";
	}

	// 특정 id로 공지사항 삭제
	@DeleteMapping("/notice/{id}")
	public String deleteNotice(@PathVariable("id") long id) {
		noticeService.delete(id);
		return "redirect:/notice/noticemanage";
	}

	// 공지사항 수정하는
	@PutMapping("/notice/{id}")
	public String updateNotice(@PathVariable("id") long id, NoticeUpdateDTO dto) {
		noticeService.updateNotice(id, dto);
		return "redirect:/notice/{id}";

	}

}
