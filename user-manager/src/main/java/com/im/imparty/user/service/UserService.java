package com.im.imparty.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.im.imparty.user.dto.UserInfoDetail;
import com.im.imparty.user.entity.UserDomain;

public interface UserService extends IService<UserDomain> {
    UserInfoDetail getUserDetail(String userName);
}
