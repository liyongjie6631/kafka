package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.demo.service.KafkaProducerListener;

@Configuration
public class ProducerConfig {
	
	@Bean
	public Map<String,Object> getDefaultFactoryArg(){
	    Map<String,Object> arg = new HashMap<>();
	    arg.put("bootstrap.servers",ConstantKafka.KAFKA_SERVER);
	    arg.put("group.id","100");
	    arg.put("retries","1");
	    arg.put("batch.size","16384");
	    arg.put("linger.ms","1");
	    arg.put("buffer.memory","33554432");
	    arg.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
	    arg.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
	    arg.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
	    arg.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
	    return arg;
	}

	@Bean
	public DefaultKafkaProducerFactory defaultKafkaProducerFactory(){
	    DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory(this.getDefaultFactoryArg());
	    return factory;
	}

	@Bean
	public KafkaTemplate kafkaTemplate(){
	    KafkaTemplate template = new KafkaTemplate(defaultKafkaProducerFactory());
	    template.setDefaultTopic(ConstantKafka.KAFKA_TOPIC1);
	    template.setProducerListener(kafkaProducerListener());
	    return template;
	}

	@Bean
	public KafkaProducerListener kafkaProducerListener(){
	    KafkaProducerListener listener = new KafkaProducerListener();
	    return listener;
	}

}
