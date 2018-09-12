package cn.eleven.basic.data.webapp.gateway.web.admin;

import cn.eleven.basic.data.rocketmq.client.dto.MQMessage;
import cn.eleven.basic.data.rocketmq.client.producer.ProducerFactory;
import cn.eleven.basic.data.user.south.api.dto.UserBaseDto;
import com.alibaba.rocketmq.client.producer.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author: eleven
 * @date: 2018/9/5 19:43
 * @description: mq测试联调对接
 */
@Slf4j
@RestController
@RequestMapping("mqTest")
public class MqAction {

    @Resource(name = "producerFactory")
    ProducerFactory producerFactory;

    @Resource(name = "producerFactory1")
    ProducerFactory producerFactory1;

    /**
     * 测试消息生产
     * @return
     */
    @GetMapping("sendMessageToPlatform")
    public SendStatus sendMessageToPlatform(){

        UserBaseDto userBaseDto = new UserBaseDto();
        userBaseDto.setName("mq学习课程");
        userBaseDto.setGrade(6);
        userBaseDto.setPhone("123456789");
        userBaseDto.setAddress("福建省福州市鼓楼区工业路611号海峡技术转移中心");
        MQMessage message = new MQMessage(userBaseDto.toString());
        message.setFrom("基础数据服务");
        message.setTo("计划发送给平台");
        producerFactory1.sendMessage(message);
        return producerFactory.sendMessage(message);
    }



}
