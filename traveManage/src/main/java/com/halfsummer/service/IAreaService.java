package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.Area;

/**
 * 地区码表 服务类
 */
public interface IAreaService extends IService<Area> {

    public Integer selectIdByAreaName(String areaName);

}
