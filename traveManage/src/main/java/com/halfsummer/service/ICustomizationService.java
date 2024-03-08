package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.Customization;

import java.util.List;

/**
 *  定制 服务类
 */
public interface ICustomizationService extends IService<Customization> {


    public List<Customization> selectListByUid(String uid);

}
