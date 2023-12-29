package com.example.mqtt.handler;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MqttMessageSender{

    private MqttGateway mqttGateway;

    /**
     * 发送mqtt消息
     *
     * @param topic   主题
     * @param message 内容
     * @return void
     */
    public void send(String topic, String message) {
        mqttGateway.sendToMqtt(topic, message);
    }

    /**
     * 发送包含qos的消息
     *
     * @param topic       主题
     * @param qos         质量
     * @param messageBody 消息体
     * @return void
     */
    public void send(String topic, int qos, JSONObject messageBody) {
        mqttGateway.sendToMqtt(topic, qos, messageBody.toString());
    }

    /**
     * 发送包含qos的消息
     *
     * @param topic   主题
     * @param qos     质量
     * @param message 消息体
     * @return void
     */
    public void send(String topic, int qos, byte[] message) {
        mqttGateway.sendToMqtt(topic, qos, message);
    }
}