package org.ibmmq.ibmmqmessagetester.config;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class MQConfiguration {
//	
//	ibm.msim.mq.queueManager = QM1
//			ibm.msim.mq.channel = DEV.APP.SVRCONN
//			ibm.msim.mq.connName = 192.168.34.10
//			ibm.msim.mq.port=1414
//			ibm.msim.mq.user = app
//			ibm.msim.mq.password = password
	
	@Value("${ibm.msim.mq.queueManager}")
	public String queueManager;
	
	@Value("${ibm.msim.mq.channel}")
	public String channel;
	
	@Value("${ibm.msim.mq.connName}")
	public String connName;
	
	@Value("${ibm.msim.mq.port}")
	public String port;
	
	@Value("${ibm.msim.mq.user}")
	public String user;
	
	@Value("${ibm.msim.mq.password}")
	public String password ;
	
	
	
	@Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
		 System.out.println(
connName + " " + port  + " " + queueManager  + " " + channel  + " " + user  + " " + password);
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(connName);
        try {
           mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        	//mqQueueConnectionFactory.setTransportType(1);
            mqQueueConnectionFactory.setCCSID(1208);
            mqQueueConnectionFactory.setChannel(channel);
            mqQueueConnectionFactory.setPort(new Integer(port));
            mqQueueConnectionFactory.setQueueManager(queueManager);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mqQueueConnectionFactory;
    }
	
	   @Bean 
	    public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) { 
	        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter(); 
	        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory); 
	        userCredentialsConnectionFactoryAdapter.setUsername(user); 
	        userCredentialsConnectionFactoryAdapter.setPassword(password); 
	        return userCredentialsConnectionFactoryAdapter; 
	        
	   }
	
	    @Bean 
	    @Primary
	    public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) { 
	        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(); 
	        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter); 
	        cachingConnectionFactory.setSessionCacheSize(500); 
	        cachingConnectionFactory.setReconnectOnException(true); 
	        return cachingConnectionFactory; 
	    }
	    
	    @Bean 
	    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) { 
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory(); 
	        configurer.configure(factory, cachingConnectionFactory); 
	        factory.setConnectionFactory(cachingConnectionFactory); 
	        factory.setConcurrency("4-8"); // core 4 threads and max 8 threads 
	        return factory; 
	    } 
	    
	    @Bean 
	    public JmsTemplate jmsQueueTemplate(CachingConnectionFactory cachingConnectionFactory) { 
	        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory); 
	        jmsTemplate.setConnectionFactory(cachingConnectionFactory); 
	        jmsTemplate.setDefaultDestinationName(""); 
	        return jmsTemplate; 
	    } 
	  
	  
//	    @Bean
//	    public Queue queue() {
//	        return new Queue("standalone.queue");
//	    }

}
