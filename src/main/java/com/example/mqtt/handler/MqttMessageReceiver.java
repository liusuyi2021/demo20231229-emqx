package com.example.mqtt.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@AllArgsConstructor
@Component
public class MqttMessageReceiver implements MessageHandler {
    @Resource
    private DataHandler dataHandler;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            MessageHeaders headers = message.getHeaders();
            //获取消息Topic
            String receivedTopic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            log.info("[获取到的消息的topic :]{} ", receivedTopic);
            //获取消息体
            String payload = (String) message.getPayload();
            log.info("[获取到的消息的payload :]{} ", payload);
            //todo ....
            //处理业务逻辑
            dataHandler.run(receivedTopic,payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}