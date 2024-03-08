package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.User;

import java.util.List;

/**
 * 用户 服务类
 */
public interface IUserService extends IService<User> {


    public ServerResponse logion(String username, String password);//登录

    public List<User> getAllUser();
}
