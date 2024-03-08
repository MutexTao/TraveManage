package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.UserPreference;

import java.util.List;

/**
 * 用户偏好 服务类
 */
public interface IUserPreferenceService extends IService<UserPreference> {

    public List<UserPreference> getUserPreferenceByUserId();
    public List<UserPreference> getAllUserPreference();



}
