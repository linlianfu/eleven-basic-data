package cn.eleven.basic.data.rocketmq.client.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/3 22:37
 * @description: 默认的mq消费者
 */
@Slf4j
public class DefaultMQConsumer implements Serializable,DisposableBean,InitializingBean {
    /**
     * broker地址
     */
    @Setter
    private String namesrvAddr;
    @Setter
    private String consumerGroup;
    @Setter
    private String topic;
    @Setter
    private String subExpression;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mq默认消费者初始化");
        startMessageListener();

    }


    public boolean startMessageListener(){

        log.info(">>>>>基础数据服务启动消息消费监听。。。。。");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(topic, subExpression);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(getRegisterMessageListener());
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



    public MessageListenerConcurrently getRegisterMessageListener(){
        return (msgs, context) -> {
            log.info(">>>>>成功接收消息，开始消费。。。。。");
            Message msg = msgs.get(0);
            log.info("接收到的消息：【{}】",new String(msg.getBody()));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        };
    }

    @Override
    public void destroy() throws Exception {

    }


}
