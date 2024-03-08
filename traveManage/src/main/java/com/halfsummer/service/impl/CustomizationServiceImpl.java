package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.CustomizationMapper;
import com.halfsummer.entity.Customization;
import com.halfsummer.service.ICustomizationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定制 服务实现类
 * 它使用了MyBatis-Plus框架提供的selectList方法查询指定用户的定制记录列表，并返回查询结果。
 * 具体实现中，它通过创建一个EntityWrapper对象并使用eq方法指定查询条件，然后调用selectList方法查询符合条件的定制记录列表。
 */
@Service
public class CustomizationServiceImpl extends ServiceImpl<CustomizationMapper, Customization> implements ICustomizationService {

    @Override
    public List<Customization> selectListByUid(String uid) {
       return selectList(new EntityWrapper<Customization>().eq("uid",uid));
    }
}
