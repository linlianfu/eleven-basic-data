package cn.eleven.basic.data.user.south.impl;

import cn.eleven.basic.data.user.south.api.IUserQueryService;
import cn.eleven.basic.data.user.south.api.arg.UserQuery;
import cn.eleven.basic.data.user.south.api.dto.UserBaseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: eleven
 * @date: 2018/9/2 20:20
 * @description:
 */
@Service("userQueryService")
public class UserQueryServiceImpl implements IUserQueryService {
    @Override
    public List<UserBaseDto> listBase(UserQuery query) {
        List<UserBaseDto> result = new ArrayList<>();
        result.add(new UserBaseDto("小林",2,"福州市","110",query.getRegion()));
        result.add(new UserBaseDto("小红",2,"福州市","110",query.getRegion()));
        result.add(new UserBaseDto(query.getName(),2,"福州市","110",query.getRegion()));
        return result;
    }
}
