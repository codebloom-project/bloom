package com.green.bloom.chatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.green.bloom.domain.entity.Answer;
import com.green.bloom.domain.entity.ChatBotIntentionRepository;
import com.green.bloom.domain.entity.EmployeeEntity;
import com.green.bloom.domain.entity.EmployeeEntityRepository;
import com.green.bloom.domain.entity.ScheduleEntity;
import com.green.bloom.domain.entity.ScheduleEntityRepository;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KomoranService {
	
	
	private final Komoran komoran;
	
	private final ChatBotIntentionRepository intention;
	
	
	public MessageDTO nlpAnalyze(String message) {
		
		KomoranResult result=komoran.analyze(message);
		result.getTokenList().forEach(token->{
			System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex()
					, token.getMorph(), token.getPos());
		});
		
		//문자에서 명사들만 추출한 목록 중복제거해서 set
		Set<String> nouns=result.getNouns().stream()
				.collect(Collectors.toSet());
		nouns.forEach((noun)->{
			System.out.println(">>>:"+ noun);
		});;//메세지에서 명사추출
		
		return analyzeToken(nouns);	
	}

	//입력된 목적어를 하나씩 파악하여 DB에서 검색하기위해 decisionTree()메서드로 전달
	private MessageDTO analyzeToken(Set<String> nouns) {
		
		LocalDateTime today=LocalDateTime.now();
		//DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("a H:mm");
		MessageDTO messageDTO=MessageDTO.builder()
								.time(today.format(timeFormatter))//시간세팅
								.build();
		
		//1차의도가 존재하는지 파악
		for(String token:nouns) {
			
			Optional<ChatBotIntention> result=decisionTree(token, null);
			if(result.isEmpty())continue;//존재하지 않으면 다음토큰 검색
			
			//1차 토근확인시 실행
			System.out.println(">>>>1차:"+token);
			//1차목록 복사
			Set<String> next=nouns.stream().collect(Collectors.toSet());
			//목록에서 1차토큰 제거
			next.remove(token);
			
			//2차분석 메서드
			AnswerDTO answer=analyzeToken(next, result).toAnswerDTO();
			
			//전화인경우 전화,전화번호 번호탐색
			
			if(token.contains("전화")||token.contains("전번")||token.contains("번호")) {
				List<PhoneInfo> phone=analyzeTokenIsPhone(next);
				answer.phone(phone);//전화인경우에만 전화 데이터 
				
			}else if(token.contains("부서")) {
				List<DeptInfo> dept = analyzeTokenIsEmpDept(next);
				answer.dept(dept);
			}else if(token.contains("일정")) {
				List<ScheduleInfo> schedule = analyzeTokenIsEmpSchedule(next);
				answer.schedule(schedule);
			}
			else if(token.contains("인사말")){
				DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
				messageDTO.today(today.format(dateFormatter));//처음 접속할때만 날짜표기
			}
			messageDTO.answer(answer);//토근에대한 응답정보
			
			
			
			return messageDTO;
		}
		//분석 명사들이 등록한 의도와 일치하는게 존재하지 않을경우 null
		AnswerDTO answer=decisionTree("기타", null).get().getAnswer().toAnswerDTO();
		messageDTO.answer(answer);//토근에대한 응답정보
		return messageDTO;
	}


	///*
	private final EmployeeEntityRepository employeeEntityRepository;
	
	private final ScheduleEntityRepository scheduleEntityRepository;
	
	//전화문의인경우 DB에서 사원을 을 찾아서 처리
	//List : 동명이인 생각
	private List<PhoneInfo> analyzeTokenIsPhone(Set<String> next) {
		for(String name : next) {
			List<EmployeeEntity> emps=employeeEntityRepository.findAllByEmpName(name);
			if(emps.isEmpty())continue;
			//존재하면
			
			return emps.stream()
					.map(EmployeeEntity::toPhoneInfo)
					.collect(Collectors.toList());

		}
		return null;
	}
	
	private List<DeptInfo> analyzeTokenIsEmpDept(Set<String> next) {
		for(String deptName : next) {
			List<EmployeeEntity> emps=employeeEntityRepository.findAllByEmpName(deptName);
			if(emps.isEmpty())continue;
			//존재하면
			
			return emps.stream()
					.map(EmployeeEntity::toDeptInfo)
					.collect(Collectors.toList());
		}
		return null;
	}
	
	private List<ScheduleInfo> analyzeTokenIsEmpSchedule(Set<String> next) {
		for(String categori : next) {
			
			boolean scdCate =false;
			if(categori.equals("과정"))scdCate=true;
			//boolean scdCate =categori.equals("과정")?true:false;
			
			 System.out.println(">>>>scdCate:"+scdCate+","+categori);
			 List<ScheduleEntity> scds = scheduleEntityRepository.findByCategori(scdCate);
			if(scds.isEmpty())continue;
			
			return scds.stream()
					.map(ScheduleEntity::toSchedule)
					.collect(Collectors.toList());
		}
		return null;
	}
	//*/

	//1차의도가 존재하면
	//하위의도가 존재하는지 파악
	private Answer analyzeToken(Set<String> next, Optional<ChatBotIntention> upper) {
		for(String token : next) {
			// 1차의도를 부모로하는 토큰이 존재하는지 파악
			Optional<ChatBotIntention> result=decisionTree(token, upper.get());
			if(result.isEmpty())continue;
			System.out.println(">>>>2차:"+token);
			return result.get().getAnswer();//1차-2차 존재하는경우 답변
		}
		return upper.get().getAnswer();//1차만 존재하는 답변
	}

	
	//의도가 존재하는지 DB에서 파악
	private Optional<ChatBotIntention> decisionTree(String token, ChatBotIntention upper) {
		return intention.findByNameAndUpper(token, upper); 
	}


}