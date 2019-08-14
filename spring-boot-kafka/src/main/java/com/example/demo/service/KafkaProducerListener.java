package com.example.demo.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

public class KafkaProducerListener implements ProducerListener {

	private Logger logger = LoggerFactory.getLogger(KafkaProducerListener.class);

    public KafkaProducerListener(){

    }

    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        logger.info("-----------------kafka发送数据成功");
        logger.info("----------topic:"+topic);
        logger.info("----------partition:"+partition);
        logger.info("----------key:"+key);
        logger.info("----------value:"+value);
        logger.info("----------RecordMetadata:"+recordMetadata);
        logger.info("-----------------kafka发送数据结束");
    }

    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception e) {
        logger.info("-----------------kafka发送数据失败");
        logger.info("----------topic:"+topic);
        logger.info("----------partition:"+partition);
        logger.info("----------key:"+key);
        logger.info("----------value:"+value);
        logger.info("-----------------kafka发送数据失败结束");
        e.printStackTrace();
    }

    /**
     * 是否启动Producer监听器
     * @return
     */
    @Override
    public boolean isInterestedInSuccess() {
        return false;
    }
}