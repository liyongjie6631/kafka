package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import com.example.demo.service.KafkaConsumerMessageListener;

@Configuration
public class ConsumerConfig {
	
	@Bean
	public Map<String,Object> getDefaultArgOfConsumer(){
	    Map<String,Object> arg = new HashMap<>();
	    arg.put("bootstrap.servers",ConstantKafka.KAFKA_SERVER);
	    arg.put("group.id","100");
	    arg.put("enable.auto.commit","false");
	    arg.put("auto.commit.interval.ms","1000");
	    arg.put("auto.commit.interval.ms","15000");
	    arg.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
	    arg.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
	    arg.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
	    arg.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

	    return arg;
	}

	@Bean
	public DefaultKafkaConsumerFactory defaultKafkaConsumerFactory(){
	    DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory(getDefaultArgOfConsumer());
	    return factory;
	}

	@Bean
	public KafkaConsumerMessageListener kafkaConsumerMessageListener(){
	    KafkaConsumerMessageListener listener = new KafkaConsumerMessageListener();
	    return listener;
	}

	/**
	 * 监听频道-log
	 * @return
	 */
	@Bean
	public ContainerProperties containerPropertiesOfLog(){
	    ContainerProperties properties = new ContainerProperties(ConstantKafka.KAFKA_TOPIC1);
	    properties.setMessageListener(kafkaConsumerMessageListener());
	    return properties;
	}

	/**
	 * 监听频道-other
	 * @return
	 */
	@Bean
	public ContainerProperties containerPropertiesOfOther(){
	    ContainerProperties properties = new ContainerProperties(ConstantKafka.KAFKA_TOPIC2);
	    properties.setMessageListener(kafkaConsumerMessageListener());
	    return properties;
	}

	@Bean(initMethod = "doStart")
	public KafkaMessageListenerContainer kafkaMessageListenerContainerOfLog(){
	    KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(defaultKafkaConsumerFactory(),containerPropertiesOfLog());
	    return container;
	}

	@Bean(initMethod = "doStart")
	public KafkaMessageListenerContainer kafkaMessageListenerContainerOfOther(){
	    KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(defaultKafkaConsumerFactory(),containerPropertiesOfOther());
	    return container;
	}

}
