package com.example.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 生产者发送消息控制类
 * @Author cxl
 * @Date 2022-01-10
 */
@RestController
@RequestMapping("send")
public class SendMessageController {

    /**
     * RabbitTemplate 提供接收/发送等方法
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/direct")
    public String direct() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "hello world";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map = new HashMap<>(16);
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        // 将消息携带绑定键值：testDirectRouting 发送到交换机testDirectExchange
        rabbitTemplate.convertAndSend("testDirectExchange", "testDirectRouting", map);
        return "ok";
    }

    @GetMapping("/topic")
    public String topic() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "man";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", map);
        return "ok";
    }

    @GetMapping("/topic2")
    public String topic2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "woman";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", map);
        return "ok";
    }

    @GetMapping("/fanout")
    public String fanout() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

}
