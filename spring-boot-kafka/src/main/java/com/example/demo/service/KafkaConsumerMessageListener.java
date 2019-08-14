package com.example.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

import com.example.demo.config.ConstantKafka;

public class KafkaConsumerMessageListener implements MessageListener<String,Object> {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumerMessageListener.class);

    public KafkaConsumerMessageListener(){

    }

    /**
     * 消息接收-LOG日志处理
     * @param record
     */
    @Override
    public void onMessage(ConsumerRecord<String, Object> record) {
        logger.info("=============kafka消息订阅=============");

        String topic = record.topic();
        String key = record.key();
        Object value = record.value();
        long offset = record.offset();
        int partition = record.partition();

        if (ConstantKafka.KAFKA_TOPIC1.equals(topic)){
            doSaveLogs(value.toString());
        }

        logger.info("-------------topic:"+topic);
        logger.info("-------------value:"+value);
        logger.info("-------------key:"+key);
        logger.info("-------------offset:"+offset);
        logger.info("-------------partition:"+partition);
        logger.info("=============kafka消息订阅=============");
    }

    private void doSaveLogs(String data){
       System.out.println(data);
        /**
         * 写入到数据库中
         */
    }
}