package io.manasobi.commons.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.manasobi.license.License;
import io.manasobi.license.LicenseJobHandler;

@Configuration
@EnableRabbit
public class AmqpConfig {

	@Autowired
	private LicenseJobHandler jobHandler;
	
	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        
        factory.setConnectionFactory(rabbitConnectionFactory());
        //factory.setMessageConverter(messageConverter());
        
        return factory;
    }

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        
        connectionFactory.setAddresses("192.168.0.9");
        connectionFactory.setVirtualHost("manasobi-host");
        connectionFactory.setUsername("manasobi");
        connectionFactory.setPassword("manasobi");
        
        return connectionFactory;
    }
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		
		rabbitTemplate.setQueue("manasobi");
		//rabbitTemplate.setRoutingKey("manasobi.key");
		//rabbitTemplate.setExchange("manasobi.key");
		rabbitTemplate.setRoutingKey("key.manasobi");
		rabbitTemplate.setExchange("manasobi");
		
		rabbitTemplate.setConnectionFactory(rabbitConnectionFactory());
			
		return rabbitTemplate;
	}
	
	@RabbitListener(queues = "manasobi")
    public void rabbitListener(License license) {
        
		System.out.println("amqp 작업 시작");
		jobHandler.process(license);
		System.out.println("amqp 작업 완료");
    }
}
