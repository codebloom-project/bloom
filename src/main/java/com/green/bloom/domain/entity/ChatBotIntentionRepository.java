package com.green.bloom.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.bloom.chatbot.ChatBotIntention;


public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntention, Long>{

	Optional<ChatBotIntention> findByName(String token);

	Optional<ChatBotIntention> findByNameAndUpper(String token, ChatBotIntention upper);

}