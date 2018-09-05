package cn.eleven.basic.data.user.south.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/2 20:17
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDto implements Serializable{
    /**
     * 姓名
     */
    private String name;
    /**
     * 年级
     */
    private int grade;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
}
