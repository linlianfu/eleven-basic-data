package cn.eleven.basic.data.user.south.api.arg;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/2 20:18
 * @description: 用户查询条件
 */
@Data
public class UserQuery implements Serializable{
    /**
     * 用户名字搜索
     */
    private String name;
    /**
     * 地区
     */
    private String region;
}
