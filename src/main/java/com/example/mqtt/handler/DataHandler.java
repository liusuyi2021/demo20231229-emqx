package com.example.mqtt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @ClassName: DataHandler
 * @Author: 刘苏义
 * @Date: 2023年12月29日14:12:25
 **/
@Component
@Slf4j
public class DataHandler {
    @Async
    public void run(String topic,String data)
    {
        log.info("开始处理{}的数据：{}",topic,data);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("结束处理{}的数据：{}",topic,data);
    }
}
