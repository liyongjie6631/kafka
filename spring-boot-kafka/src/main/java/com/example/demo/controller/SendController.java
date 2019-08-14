package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.been.ResultResp;
import com.example.demo.service.KafkaProducerServer;

@RestController
@RequestMapping("kafka")
public class SendController {
	
	@Autowired
	private KafkaProducerServer kafkaProducerServer;
	
	@RequestMapping("send")
	public String sendmsg(String msg) {
		
		ResultResp rr = kafkaProducerServer.send("topic1", msg, true, 1, "web");
		
		return rr.toString();
	}
	

}
