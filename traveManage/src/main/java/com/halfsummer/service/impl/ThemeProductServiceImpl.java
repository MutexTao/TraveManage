package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.ThemeProductMapper;
import com.halfsummer.entity.ThemeProduct;
import com.halfsummer.service.IThemeProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  主题产品 服务实现类
 */
@Service
public class ThemeProductServiceImpl extends ServiceImpl<ThemeProductMapper, ThemeProduct> implements IThemeProductService {

    @Override
    public List<ThemeProduct> selectByPid(String pid) {

        return selectList(new EntityWrapper<ThemeProduct>().eq("product_id",pid));
    }
}
