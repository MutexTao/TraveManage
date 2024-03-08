package com.halfsummer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.halfsummer.entity.ProductOrder;

/**
 *  订单Mapper 接口
 */
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {

    public Boolean submitStars(ProductOrder productOrder);
}
