package com.example.demo.simple;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**生产者示例*/
public class KafkaProducerTest {
    private static  Producer<String, String> producer;
    public final static String TOPIC="test1";
    public KafkaProducerTest(){
        Properties props=new Properties();
        props.put("bootstrap.servers","192.168.159.131:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",16384);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
       
        producer = new KafkaProducer<String,String>(props);
    }

    public void produce(){
        int messageNum=100;
        final int count=120;
        while(messageNum<count){
            String key=String.valueOf(messageNum);
            String data="@@@@hello kafka message"+key;
            producer.send(new ProducerRecord<String,String>(TOPIC,key,data));
            System.out.println(data);
            messageNum++;
        }
        producer.close(); //注意发送完数据要关闭，否则可能出错
    }
    public  static void main(String[] args){
        new KafkaProducerTest().produce();
    }
}