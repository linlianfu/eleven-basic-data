package cn.eleven.basic.data.user.south.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: eleven
 * @date: 2018/9/2 20:17
 * @description:
 */
@Data
@AllArgsConstructor
public class UserBaseDto implements Serializable{

    private String name;
}
