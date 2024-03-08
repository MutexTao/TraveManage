package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.ProductOrder;
import com.halfsummer.entity.ProductSell;

import java.util.List;

/**
 * 产品订单 服务类
 */
public interface IProductOrderService extends IService<ProductOrder> {

    public ServerResponse createOrder(ProductOrder productOrder, ProductSell productSell)throws Exception;

    public List myOrderList(String uid);

    public List<ProductOrder> getProductOrderByUserid(String uid);

    public Integer submitStars(ProductOrder productOrder);
}
