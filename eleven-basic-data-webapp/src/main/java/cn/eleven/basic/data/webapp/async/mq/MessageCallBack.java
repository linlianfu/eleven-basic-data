package cn.eleven.basic.data.webapp.async.mq;

import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eleven
 * @date 2018/9/16
 * @description 消息发送成功回调函数
 */
@Slf4j
public class MessageCallBack implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("异步消息回调函数-消息发送成功:{}",sendResult);

    }

    @Override
    public void onException(Throwable e) {
        log.info("消息发送失败，{}",e);

    }
}
