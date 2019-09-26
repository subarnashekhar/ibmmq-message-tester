package org.ibmmq.ibmmqmessagetester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendMessage {
	
    @Autowired
    JmsTemplate jmsTemplate;
	
	@GetMapping("/send")
	public String send(){
	    try{
	        jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World...........!");
	        return "OK";
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}

}
