package cn.eleven.basic.data.rocketmq.client.producer;

import cn.eleven.basic.data.rocketmq.client.dto.MQMessage;
import cn.eleven.common.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.impl.CommunicationMode;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.List;

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
    @Setter
    private List<String> keys;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mq生产者初始化");

    }

    /**
     * 同步发送消息 {@link CommunicationMode#SYNC}
     * @param message
     * @return
     */
    public SendStatus sendMessage(MQMessage message){
        return send(message,null);
    }

    /**
     * 异步发送消息{@link CommunicationMode#ASYNC}
     * @param message
     * @param callback
     */
    public void sendMessage(MQMessage message, SendCallback callback){
        send(message,callback);
    }

    private SendStatus send(MQMessage message, SendCallback callback){
        log.info(">>>>>开始发送消息,topic:【{}】,tags:【{}】",topic,tags);
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
            message.packMessage(topic,tags);
            Message msg = new Message(topic,
                    tags,
                    JSONObject.toJSONString(message).getBytes());
            if (CollectionUtils.isNotEmpty(keys))
              msg.setKeys(keys);
            if (callback == null){
                SendResult result =  producer.send(msg);
                log.info("消息发送结束，时间：【{}】,id【{}】,result【{}】，context【{}】",
                        DateUtil.toString(message.getHead().getSendTime(), DateUtil.DatePatten.PATTEN_TO_SECOND),
                        result.getMsgId(),
                        result.getSendStatus(),new String(msg.getBody()));
                return result.getSendStatus();
            }else {
               producer.send(msg,callback);
               log.info(">>>异步消息发送完成");
            }

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
