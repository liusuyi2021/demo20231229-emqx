package com.example;

import com.example.domain.User;
import com.example.mqtt.handler.MqttMessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@Slf4j
class Demo20231229EmqxApplicationTests {
    @Resource
    MqttMessageSender messageSender;

    ObjectMapper mapper = new ObjectMapper();
    @Test
    void contextLoads() throws IOException {
        //对象转json
        log.debug(mapper.writeValueAsString(new User("张三", 20,new Date())));
        String carJson = "{ \"name\" : \"Mercedes\", \"age\" : 5 }";
        //json转对象
        User user = mapper.readValue(carJson, User.class);
        log.debug(user.getName()+"_"+user.getAge());
        //对象转为byte数组
        byte[] byteArr = mapper.writeValueAsBytes(user);
        System.out.println(byteArr);
        //byte数组转为对象
        User student= mapper.readValue(byteArr, User.class);
        System.out.println(student);

        //List集合转json
        List<User> studentList= new ArrayList<>();
        studentList.add(new User("hyl1" ,20,new Date() ));
        studentList.add(new User("hyl2" ,21,new Date() ));
        studentList.add(new User("hyl3" ,22,new Date() ));
        studentList.add(new User("hyl4" ,23,new Date() ));
        String jsonStr = mapper.writeValueAsString(studentList);
        System.out.println(jsonStr);

        //json转List集合
        List<User> studentList2 = mapper.readValue(jsonStr, List.class);
        System.out.println("字符串转集合：" + studentList2 );

        //MAP集合转json
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("name", "22");
        testMap.put("age", 20);
        String jsonStr1 = mapper.writeValueAsString(testMap);
        System.out.println(jsonStr1);

        //json转MAP集合
        Map<String, Object> testMapDes = mapper.readValue(jsonStr1, Map.class);
        System.out.println(testMapDes);

        // 修改时间格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        User student1 = new User ("hyl",21, new Date());
        String jsonStr2 = mapper.writeValueAsString(student1);
        System.out.println(jsonStr2);
    }

    @Test
    void mqttTest()
    {
        messageSender.send("topic1","hello topic1");
        messageSender.send("topic2","hello topic2");
    }
}