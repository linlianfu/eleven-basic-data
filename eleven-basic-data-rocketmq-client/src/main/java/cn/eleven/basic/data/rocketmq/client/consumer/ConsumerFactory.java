package cn.eleven.basic.data.rocketmq.client.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
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
import java.util.concurrent.TimeUnit;

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
    /**
     * 消费集群
     */
    @Setter
    private String consumerGroup;
    /**
     * 订阅topic
     */
    @Setter
    private String topic;
    /**
     * 订阅的tag
     */
    @Setter
    private String subExpression;
    /**
     * 订阅的tag是否是顺序消费
     */
    @Setter
    private boolean order;
    /**
     *一次消费拉取的消息条数，默认情况是1条
     */
    @Setter
    private int consumeMessageBatchMaxSize = 1;
    @Setter
    private Map<String,List<String>> topicTagMap;
    private static Map<String,DefaultMQPushConsumer> consumerMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mq消费者初始化");
        startMessageListener();
    }


    private void startMessageListener(){

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
            //设置一次消费几条消息
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            //设置消费者最多重新消费次数
//            consumer.setMaxReconsumeTimes(5);
//            log.info("最大重新消费次数：{}",consumer.getMaxReconsumeTimes());
            if (order){
                consumer.registerMessageListener(registerOrderMessageListener());
            }else {
                consumer.registerMessageListener(registerConcurrentlyMessageListener());
            }
            consumer.start();
            consumerMap.put(topic,consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 注册普通消费监听
     * @return
     */
    private MessageListenerConcurrently registerConcurrentlyMessageListener(){
        return (msgs, context) -> {
            MessageExt msg = msgs.get(0);
//            log.info(">>>>>【{}】成功接收消息，来源topic:{},tags:{}",
//                    DateUtil.getCurrentDateString(DateUtil.DatePatten.PATTEN_TO_SECOND),
//                    msg.getTopic(),msg.getTags());
//            log.info("此次消费拉去消息数目：{}条",msgs.size());
            log.info("Concurrently:消息队列：{}，消息key:{},消息体：【{}】",msg.getQueueId(),msg.getKeys(),new String(msg.getBody()));
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            log.info("队列：{}，消息key:{}消费完成",msg.getQueueId(),msg.getKeys());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        };
    }

    /**
     * 注册顺序消费监听
     * @return
     */
    private MessageListenerOrderly registerOrderMessageListener(){
      return (msgs, context) -> {
          MessageExt msg = msgs.get(0);
//          log.info(">>>>>【{}】成功接收消息，来源topic:{},tags:{}",
//                  DateUtil.getCurrentDateString(DateUtil.DatePatten.PATTEN_TO_SECOND),
//                  msg.getTopic(),msg.getTags());
          log.info("Order:消息队列：{}，消息key:{},消息体：【{}】",msg.getQueueId(),msg.getKeys(),new String(msg.getBody()));
          return ConsumeOrderlyStatus.SUCCESS;
      };
    }

    @Override
    public void destroy() throws Exception {

    }


}
