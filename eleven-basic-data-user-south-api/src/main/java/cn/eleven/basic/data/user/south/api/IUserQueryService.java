package cn.eleven.basic.data.user.south.api;

import cn.eleven.basic.data.user.south.api.arg.UserQuery;
import cn.eleven.basic.data.user.south.api.dto.UserBaseDto;

import java.util.List;

/**
 * @author: eleven
 * @date: 2018/9/2 20:16
 * @description:
 */
public interface IUserQueryService {

    List<UserBaseDto> listBase(UserQuery query);

}
