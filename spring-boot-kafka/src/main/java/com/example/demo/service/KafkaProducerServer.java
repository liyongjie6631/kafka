package com.example.demo.service;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.example.demo.been.ResultResp;
import com.example.demo.config.ConstantKafka;


@Service
public class KafkaProducerServer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static final String ROLE_LOG = "log";
    public static final String ROLE_web = "web";
    public static final String ROLE_APP = "app";

    /**
     * 发送消息
     * @param topic 频道
     * @param msg 消息对象
     * @param isUsePartition 是否使用分区
     * @param partitionNum 分区数，如果isUsePartition为true，此数值必须>0
     * @param role 角色：app,web
     * @return
     * @throws IllegalServiceException
     */
    public ResultResp<Void> send(String topic, Object msg, boolean isUsePartition, Integer partitionNum, String role) {
        if (role == null){
            role = ROLE_LOG;
        }

        String key = role + "_" + msg.hashCode();
        String valueString = msg.toString();

        if (isUsePartition) {
            //表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            return checkProRecord(result);
        } else {
            ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(topic, key, valueString);
            return checkProRecord(result);
        }
    }

    /**
     * 根据key值获取分区索引
     *
     * @param key
     * @param num
     * @return
     */
    private int getPartitionIndex(String key, int num) {
        if (key == null) {
            Random random = new Random();
            return random.nextInt(num);
        } else {
            int result = Math.abs(key.hashCode()) % num;
            return result;
        }
    }

    /**
     * 检查发送返回结果record
     *
     * @param res
     * @return
     */

    private ResultResp<Void> checkProRecord(ListenableFuture<SendResult<String, Object>> res) {
        ResultResp<Void> resp = new ResultResp<>();
        resp.setCode(ConstantKafka.KAFKA_NO_RESULT_CODE);
        resp.setInfo(ConstantKafka.KAFKA_NO_RESULT_MES);

        if (res != null) {
            try {
                SendResult r = res.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    resp.setCode(ConstantKafka.SUCCESS_CODE);
                    resp.setInfo(ConstantKafka.SUCCESS_MSG);
                } else {
                    resp.setCode(ConstantKafka.KAFKA_NO_OFFSET_CODE);
                    resp.setInfo(ConstantKafka.KAFKA_NO_OFFSET_MES);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                resp.setCode(ConstantKafka.KAFKA_SEND_ERROR_CODE);
                resp.setInfo(ConstantKafka.KAFKA_SEND_ERROR_MES + ":" + e.getMessage());

            } catch (ExecutionException e) {
                e.printStackTrace();
                resp.setCode(ConstantKafka.KAFKA_SEND_ERROR_CODE);
                resp.setInfo(ConstantKafka.KAFKA_SEND_ERROR_MES + ":" + e.getMessage());
            }
        }

        return resp;
    }

}