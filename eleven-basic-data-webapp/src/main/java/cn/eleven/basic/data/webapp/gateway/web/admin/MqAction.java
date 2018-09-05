package cn.eleven.basic.data.webapp.gateway.web.admin;

import cn.eleven.basic.data.rocketmq.client.consumer.DefaultMQConsumer;
import cn.eleven.basic.data.rocketmq.client.producer.ProducerFactory;
import cn.eleven.basic.data.user.south.api.dto.UserBaseDto;
import com.alibaba.rocketmq.client.producer.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: eleven
 * @date: 2018/9/5 19:43
 * @description: mq测试联调对接
 */
@Slf4j
@RestController
@RequestMapping("mqTest")
public class MqAction {

    @Autowired
    ProducerFactory producerFactory;

    @Autowired
    DefaultMQConsumer consumer;
    /**
     * 测试消息生产
     * @return
     */
    @GetMapping("produceMessage")
    public SendStatus produceMessage(){

        UserBaseDto userBaseDto = new UserBaseDto();
        userBaseDto.setName("mq学习课程");
        userBaseDto.setGrade(6);
        userBaseDto.setPhone("123456789");
        userBaseDto.setAddress("福建省福州市鼓楼区工业路611号海峡技术转移中心");
        return producerFactory.sendMessage(userBaseDto.toString().getBytes());
    }



}
