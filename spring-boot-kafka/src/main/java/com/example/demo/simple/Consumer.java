package com.example.demo.simple;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
 
/******************************************************
 ****** @ClassName   : Consumer.java                                            
 ****** @author      : milo ^ ^                     
 ****** @date        : 2018 03 14 15:50     
 ****** @version     : v1.0.x                      
 *******************************************************/
public class Consumer {
 
 //   static Logger log = Logger.getLogger(Producer.class);
 
    private static final String TOPIC = "milo2";
    private static final String BROKER_LIST = "192.168.159.131:9092";
    private static KafkaConsumer<String,String> consumer = null;
 
    static {
        Properties configs = initConfig();
        consumer = new KafkaConsumer<String, String>(configs);
    }
 
    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers",BROKER_LIST);
        properties.put("group.id","0");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.offset.reset", "earliest");
        return properties;
    }
 
 
    public static void main(String[] args) {
    	consumer.subscribe(Arrays.asList(TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }
}