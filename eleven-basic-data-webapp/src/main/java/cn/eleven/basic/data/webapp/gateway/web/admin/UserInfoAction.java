package cn.eleven.basic.data.webapp.gateway.web.admin;

import cn.eleven.basic.data.user.south.api.IUserQueryService;
import cn.eleven.basic.data.user.south.api.arg.UserQuery;
import cn.eleven.basic.data.user.south.api.dto.UserBaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: eleven
 * @date: 2018/9/2 19:43
 * @description: 用户信息接口
 */
@Slf4j
@RestController
@RequestMapping("userInfo")
public class UserInfoAction {
    @Autowired
    IUserQueryService service;

    @RequestMapping("listBase")
    public List<UserBaseDto> listBase(UserQuery query){
        log.info("111111111");
        return service.listBase(query);
    }
}
