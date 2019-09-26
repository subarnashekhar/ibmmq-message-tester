package org.ibmmq.ibmmqmessagetester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IbmmqMessageTesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmmqMessageTesterApplication.class, args);
	}

}
