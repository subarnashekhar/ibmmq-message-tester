package org.ibmmq.ibmmqmessagetester.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
	
	
	@JmsListener(destination = "DEV.QUEUE.1")
	public void consume(String message) {
		System.out.println("received message" + message);
	}

}
