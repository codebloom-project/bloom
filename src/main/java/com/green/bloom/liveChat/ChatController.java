package com.green.bloom.liveChat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {
	

	 	@GetMapping("/chat")
	    public String chatGET(){

	        log.info("@ChatController, chat GET()");
	        
	        return "chat";
	    }
	    @GetMapping("/live-chat")
	    public String liveChat() {
	    	return "liveChat/live-chat.html";
	    }
	    

}
