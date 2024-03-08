package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.UserPreferenceMapper;
import com.halfsummer.entity.UserPreference;
import com.halfsummer.service.IUserPreferenceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户评分 服务实现类
 */
@Service
public class UserPreferenceServiceImpl extends ServiceImpl<UserPreferenceMapper, UserPreference> implements IUserPreferenceService {

    /**
     * @return
     *
     * 这段代码是一个实现了getUserPreferenceByUserId()方法的重写方法。该方法通过创建一个EntityWrapper实例，
     * 并使用其调用selectList()方法查询数据库表中所有的UserPreference对象，并将查询结果返回给调用方。
     */
    @Override
    public List<UserPreference> getUserPreferenceByUserId() {
        EntityWrapper<UserPreference> entityWrapper=new EntityWrapper();
        List<UserPreference> userPreferenceList=this.baseMapper.selectList(entityWrapper);
        return userPreferenceList;
    }

    /**
     *
     * @return
     *
     * 这段代码是一个实现了特定接口的方法，其目的是获取所有用户的偏好信息列表。
     * 代码首先创建一个实体包装器EntityWrapper<UserPreference>，然后使用它查询数据库中的所有用户偏好信息列表，
     * 并返回结果列表。如果数据库中没有任何用户偏好信息，则返回一个空列表。
     */
    @Override
    public List<UserPreference> getAllUserPreference() {
        EntityWrapper<UserPreference> entityWrapper=new EntityWrapper();
        List<UserPreference> userPreferenceList=this.baseMapper.selectList(entityWrapper);
        return userPreferenceList;
    }
}
