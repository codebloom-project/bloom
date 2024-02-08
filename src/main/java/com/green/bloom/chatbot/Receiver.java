package com.green.bloom.chatbot;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Receiver {

	private final SimpMessagingTemplate smt;

	private final KomoranService komoranService;
	private final TemplateEngine templateEngine;

	// RabbitTemplate template 에서 전달된 메세지가 전송된다
	public void receiveMessage(Question dto) {
		System.out.println(">>>>" + dto);

		// komoran을 사용해서
		// 의도분석 -> 응답 메세지 작성
		MessageDTO msg = komoranService.nlpAnalyze(dto.getContent());
		
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("msg", msg);
		
		String htmlResponse = templateEngine.process("common/bot-message", thymeleafContext);
		
		// 응답메세지 보내기
		smt.convertAndSend("/topic/question/" + dto.getKey(), htmlResponse);
	}

}
