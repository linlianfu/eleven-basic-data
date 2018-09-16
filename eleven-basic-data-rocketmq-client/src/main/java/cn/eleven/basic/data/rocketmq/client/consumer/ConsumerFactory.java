package cn.eleven.basic.data.rocketmq.client.consumer;

import cn.eleven.common.date.DateUtil;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: eleven
 * @date: 2018/9/3 22:37
 * @description: 消费者工厂类
 *  1.注册监听，通过查找系统下的所有订阅的topic，逐个注册监听；
 *  注意：一个topic下可能存在多个topic，则考虑是否逐个注册监听
 *  2.消费回调
 */
@Slf4j
public class ConsumerFactory implements Serializable,DisposableBean,InitializingBean {
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
    @Setter
    private Map<String,List<String>> topicTagMap;
    private static Map<String,DefaultMQPushConsumer> consumerMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mq消费者初始化");
        startMessageListener();
    }


    public boolean startMessageListener(){

        log.info(">>>>>基础数据服务启动消息消费监听。。。。。");
        log.info(">topicList:{}",topicTagMap);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息
            log.info("消费者启动订阅关系，订阅topic:【{}】,subExpression:【{}】",topic,subExpression);
            consumer.subscribe(topic, subExpression);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(getRegisterMessageListener());
            //设置消费者最多重新消费次数
            consumer.setMaxReconsumeTimes(0);
            consumer.start();
            log.info("最大重新消费次数：{}",consumer.getMaxReconsumeTimes());
            consumerMap.put(topic,consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



    public MessageListenerConcurrently getRegisterMessageListener(){
        return (msgs, context) -> {
            MessageExt msg = msgs.get(0);
            log.info(">>>>>【{}】成功接收消息，来源topic:{},tags:{}",
                    DateUtil.getCurrentDateString(DateUtil.DatePatten.PATTEN_TO_SECOND),
                    msg.getTopic(),msg.getTags());
            log.info("接收到的消息：【{}】",new String(msg.getBody()));
//            log.info(">>>消费次数：{}",msg.getReconsumeTimes());
            log.info("消息全部内容：{}",msg);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        };
    }

    @Override
    public void destroy() throws Exception {

    }


}
