package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.UserActionMapper;
import com.halfsummer.entity.UserAction;
import com.halfsummer.service.IUserActionService;
import org.springframework.stereotype.Service;

/**
 *  用户行为 服务实现类
 */
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements IUserActionService {
}
