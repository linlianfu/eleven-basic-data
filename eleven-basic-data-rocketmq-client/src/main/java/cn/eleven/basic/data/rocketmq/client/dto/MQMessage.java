package cn.eleven.basic.data.rocketmq.client.dto;

import cn.eleven.common.date.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/5 21:58
 * @description: mq消息对象
 */
@Data
public class MQMessage implements Serializable {
    /**
     * 消息头部信息
     */
    Head head;
    /**
     * 消息内容
     */
    private String body;

    public MQMessage(String body){
        head = new Head();
        this.body = body;
    }

    public void setFrom(String from){
        head.setFrom(from);
    }
    public void setTo(String to){
        head.setTo(to);
    }

    public void packMessage(String topic,String tags){
        head.setTopic(topic);
        head.setTags(tags);
        head.setSendTime(DateUtil.getCurrentDate(DateUtil.DatePatten.PATTEN_TO_SECOND));
    }

}
