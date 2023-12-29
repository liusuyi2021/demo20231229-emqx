package com.example.mqtt.handler2;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Arrays;

/**
 * mqtt回调处理类
 */
@Slf4j(topic = "mqtt")
public class MqttConsumerCallback implements MqttCallbackExtended {

    private MqttClient client;
    private String[] topic;
    private int[] qos;

    public MqttConsumerCallback(MqttClient client,  String[] topic, int[] qos) {
        this.client = client;
        this.topic = topic;
        this.qos = qos;
    }

    /**
     * 断开连接事件
     */
    @Override
    public void connectionLost(Throwable cause) {
        //log.error("断开连接事件:{}", cause.getMessage());
    }

    /**
     * 接收到消息调用令牌中调用
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    /**
     * 消息处理
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            // subscribe后得到的消息会执行到这里面
            log.info("接收消息 【主题】:" + topic + " 【内容】:" + new String(message.getPayload()));
        } catch (Exception e) {
            log.info("处理mqtt消息异常:" + e);
        }
    }

    /**
     * mqtt连接后订阅主题
     */
    @Override
    public void connectComplete(boolean b, String s) {
        try {
            if (null != topic && null != qos) {
                if (client.isConnected()) {
                    client.subscribe(topic, qos);
                    log.info("mqtt连接成功，客户端ID:{}", client.getClientId());
                    log.info("--订阅主题:{} QOS:{}" , Arrays.toString(topic),Arrays.toString(qos));
                } else {
                    log.info("mqtt连接失败，客户端ID：" + client.getClientId());
                }
            }
        } catch (Exception e) {
            log.info("mqtt订阅主题异常:" + e);
        }
    }
}