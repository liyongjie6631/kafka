package com.example.demo.simple;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerTest {

    private static Consumer<String,String> consumer;
    public final static String TOPIC="test1";

    public KafkaConsumerTest(){
        Properties props=new Properties();
        props.put("bootstrap.servers","192.168.159.131:9092");
        props.put("group.id","test-consumer-group"); //组号

        props.put("enable.auto.commit","true"); //如果value合法，将自动提交偏移量
        props.put("auto.commit.interval.ms","1000"); //设置多久更新一次被消费信息的偏移量
        props.put("session.timeout.ms","30000"); //设置会话响应时间，超过可以放弃消费或者直接消费下一条
        props.put("auto.offset.reset","earliest"); //自动重置Offset
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
       props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        consumer=new KafkaConsumer<String,String>(props);
    }

    public void consume(){
        consumer.subscribe(Arrays.asList(TOPIC));
        while(true){
            ConsumerRecords<String,String> records=consumer.poll(100); 
            for(ConsumerRecord<String,String> record:records){
                System.out.println("get message:"+record.key()+"---"+record.value());
            }
        }
    }

    public static void main(String[] args){
        new KafkaConsumerTest().consume();
    }
}