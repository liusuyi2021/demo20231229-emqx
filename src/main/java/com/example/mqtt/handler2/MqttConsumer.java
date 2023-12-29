package com.example.mqtt.handler2;

/**
 * @Description: mqtt消费客户端
 * @ClassName: MqttConsumer
 * @Author: 刘苏义
 * @Date: 2023年05月29日9:55
 * @Version: 1.0
 **/

import com.example.mqtt.config.MqttConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class MqttConsumer {
    @Resource
    private MqttConfiguration mqttConfiguration;
    private static MqttClient client;

    public void run(ApplicationArguments args){
        connect();
    }

    /**
     * 连接mqtt服务器
     */
    private void connect() {
        try {
            // 1 客户端初始化
            createClient();
            // 2 设置消费回调/主题/消息发布质量
            String[] topic = mqttConfiguration.getTopic();
            int[] qos = getQos(topic.length);
            client.setCallback(new MqttConsumerCallback(client, topic, qos));
            // 5 连接
            client.connect();
        } catch (Exception e) {
            log.error("mqtt连接异常：" + e);
        }
    }

    /**
     * 创建客户端  --- 1 ---
     */
    public void createClient() {
        try {
            if (null == client) {
                String url = new StringBuilder()
                        .append(mqttConfiguration.getProtocol().trim())
                        .append("://")
                        .append(mqttConfiguration.getHost().trim())
                        .append(":")
                        .append(mqttConfiguration.getPort())
                        .toString();
                client = new MqttClient(url, mqttConfiguration.getClientId(), new MemoryPersistence());
            }
            log.info("--创建mqtt客户端");
        } catch (Exception e) {
            log.error("创建mqtt客户端异常：" + e);
        }
    }

    /**
     * qos   --- 2 ---
     */
    public int[] getQos(int length) {

        int[] qos = new int[length];
        for (int i = 0; i < length; i++) {
            /**
             *  MQTT协议中有三种消息发布服务质量:
             *
             * QOS0： “至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
             * QOS1： “至少一次”，确保消息到达，但消息重复可能会发生。
             * QOS2： “只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果，资源开销大
             */
            qos[i] = 1;
        }
        return qos;
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            log.info("topic:" + topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布，非持久化
     * <p>
     * qos根据文档设置为1
     *
     * @param topic
     * @param msg
     */
    public static void publish(String topic, String msg) {
        publish(1, false, topic, msg);
    }

    /**
     * 发布
     */
    public static void publish(int qos, boolean retained, String topic, String pushMessage) {
        log.info("【主题】:" + topic + "【qos】:" + qos + "【pushMessage】:" + pushMessage);
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        try {
            message.setPayload(pushMessage.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("mqtt编码异常：" + e.getMessage());
        }
        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.error("topic：" + topic + " 不存在");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
            if (token.isComplete()) {
                log.info("消息发送成功");
            }
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}