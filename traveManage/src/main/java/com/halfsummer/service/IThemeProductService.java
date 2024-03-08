package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.ThemeProduct;

import java.util.List;

/**
 * 主题产品 服务类
 */
public interface IThemeProductService extends IService<ThemeProduct> {

    public List<ThemeProduct>selectByPid(String pid);

}
