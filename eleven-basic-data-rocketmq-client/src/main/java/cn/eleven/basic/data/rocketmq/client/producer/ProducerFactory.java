package cn.eleven.basic.data.rocketmq.client.producer;

import cn.eleven.basic.data.rocketmq.client.dto.MQMessage;
import cn.eleven.common.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/4 21:19
 * @description:  MQ生产者工厂
 * 1.获取指定topic下的所有tag，当没有指定发送那个tag时，默认发送到所有tag
 * 2.
 */
@Slf4j
public class ProducerFactory implements DisposableBean,InitializingBean ,Serializable{
    /**
     * broker地址
     */
    @Setter
    private String namesrvAddr;
    @Setter
    private String producerGroup;
    @Setter
    private String topic;
    @Setter
    private String tags;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mq生产者初始化");

    }


    public SendStatus sendMessage(MQMessage message){
        log.info(">>>>>开始发送消息,topic:【{}】,tags:【{}】",topic,tags);
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
            message.packMessage(topic,tags);
            Message msg = new Message(topic,
                    tags,
                    JSONObject.toJSONString(message).getBytes());
            SendResult result = producer.send(msg);
            log.info("消息发送结束，时间：【{}】,id【{}】,result【{}】，context【{}】",
                    DateUtil.toString(message.getHead().getSendTime(), DateUtil.DatePatten.PATTEN_TO_SECOND),
                    result.getMsgId(),
                    result.getSendStatus(),new String(msg.getBody()));
            return result.getSendStatus();
        } catch (Exception e) {
            log.error("消息发送失败，异常：{}", ExceptionUtils.getStackTrace(e));
        }finally{
            producer.shutdown();
        }
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }


}
