package com.example.mqtt.handler2;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * @Description:
 * @ClassName: MyMqttActionListener
 * @Author: 刘苏义
 * @Date: 2023年12月29日11:45:37
 **/
public class MyMqttActionListener implements IMqttActionListener {
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        // 连接成功后的处理逻辑
        System.out.println("连接成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        // 连接失败后的处理逻辑
        System.out.println("连接失败");
    }
}
