package cn.eleven.basic.data.rocketmq.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/5 21:58
 * @description: 消息头
 */
@Data
public class Head implements Serializable{
    /**
     *from
     */
    private String from;
    /**
     * to
     */
    private String to;
    /**
     * topic
     */
    private String topic;
    /**
     * tags
     */
    private String tags;
    /**
     * 发送时间
     */
    private String sendTime;

}
