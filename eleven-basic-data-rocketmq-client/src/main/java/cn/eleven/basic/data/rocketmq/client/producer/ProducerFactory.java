package cn.eleven.basic.data.rocketmq.client.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.MixAll;
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
 * @description:  MQ生产者
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


    public SendStatus sendMessage(byte[] messageBody){
        log.info(">>>>>开始发送消息。。。。");
        DefaultMQProducer producer = new DefaultMQProducer(MixAll.DEFAULT_CONSUMER_GROUP);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();

            Message msg = new Message(topic,
                    tags,
                    messageBody);
            SendResult result = producer.send(msg);
            log.info("id【{}】,result【{}】，context【{}】",result.getMsgId(),result.getSendStatus(),new String(msg.getBody()));
            log.info(">>>>>消息发送结束.....");
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
