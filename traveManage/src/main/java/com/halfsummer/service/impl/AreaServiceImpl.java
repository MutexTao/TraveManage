package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.AreaMapper;
import com.halfsummer.entity.Area;
import com.halfsummer.service.IAreaService;
import org.springframework.stereotype.Service;

/**
 * 地区码表 服务实现类
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    /**
     *
     * @param areaName
     * @return
     *
     * 首先通过Mybatis-plus框架提供的EntityWrapper类创建一个条件构造器，
     * 然后调用selectOne方法根据条件查询一个区域对象。如果查询到区域对象，就返回该对象的ID，否则返回null
     */
    @Override
    public Integer selectIdByAreaName(String areaName) {
        EntityWrapper<Area> entityWrapper=new EntityWrapper();
        entityWrapper.eq("areaName",areaName);
        Area area=selectOne(entityWrapper);
        if (null==area){
            return null;
        }
        return area.getAreaId();
    }
}
