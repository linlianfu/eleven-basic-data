package cn.eleven.basic.data.rocketmq.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 发送时间
     */
    private Date sendTime;
}
