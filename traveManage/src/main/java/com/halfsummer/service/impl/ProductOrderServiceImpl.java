package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.dao.ProductOrderMapper;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductOrder;
import com.halfsummer.entity.ProductSell;
import com.halfsummer.service.IProductOrderService;
import com.halfsummer.service.IProductSellService;
import com.halfsummer.service.IProductService;
import com.halfsummer.util.IDUtils;
import com.halfsummer.vo.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品订单 服务实现类
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements IProductOrderService {


    @Autowired
    private IProductSellService productSellService;
    @Autowired
    private IProductService productService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServerResponse createOrder(ProductOrder productOrder, ProductSell productSell) throws Exception {
        //设置id
        productOrder.setOrderNo(IDUtils.genItemId());
        //计算价格
        int num = productOrder.getPruductNum();
        double unitPrice = productSell.getpPrice();
        productOrder.setPayment(new BigDecimal(num * unitPrice));
        //判断库存
        if (productSell.getStock() <= 0) {
            return ServerResponse.createByErrorMessage("已销售完");
        }
        //设置房型
        productOrder.setHouseTypeName(productSell.getHouseTypeName());
        //扣除库存
        productSell.setStock(productSell.getStock() - num);
        //增加销量
        Product product = productService.selectById(productSell.getPid());
        product.setSell(product.getSell() + num);
        product.setUpdateTime(new Date());
        //更新
        productSell.setUpdateTime(new Date());
        if (!productSellService.updateById(productSell)) {
            throw new Exception();
        }
        if (!productService.updateById(product)) {
            throw new Exception();
        }

        //取消支付功能，默认支付成功
        productOrder.setStatus(20);
        productOrder.setCreateTime(new Date());
        productOrder.setPaymentTime(new Date());
        if (!insert(productOrder)) {
            throw new Exception();
        }
        return ServerResponse.createBySuccess();
    }

    public List myOrderList(String uid) {
        EntityWrapper<ProductOrder> entityWrapper = new EntityWrapper();
        entityWrapper.eq("user_id", uid);
        entityWrapper.orderBy("create_time", false);

        return splicing(selectList(entityWrapper));
    }

    @Override
    public List<ProductOrder> getProductOrderByUserid(String uid) {
        EntityWrapper<ProductOrder> entityWrapper = new EntityWrapper();
        entityWrapper.eq("user_id", uid);
        List<ProductOrder> productOrderList = this.baseMapper.selectList(entityWrapper);
        return productOrderList;
    }

    @Override
    public Integer submitStars(ProductOrder productOrder) {
        EntityWrapper<ProductOrder> entityWrapper = new EntityWrapper();
        entityWrapper.eq("id", productOrder.getId());
        Integer flag = this.baseMapper.update(productOrder,entityWrapper);
        return flag;
    }

    private List<OrderList> splicing(List<ProductOrder> productOrders) {
        List<OrderList> orderLists = new ArrayList<>();
        Product product;
        ProductSell productSell;
        for (ProductOrder productOrder : productOrders) {

            OrderList orderList = new OrderList();
            orderList.setOid(productOrder.getId());
            orderList.setPid(productOrder.getProductId());
            orderList.setOrderNum(Long.toString(productOrder.getOrderNo()));
            orderList.setPayment(productOrder.getPaymentTime());
            orderList.setPrice(productOrder.getPayment().doubleValue());
            orderList.setPruductNum(productOrder.getPruductNum());
            product = productService.selectById(productOrder.getProductId());
            orderList.setTitle(product.getTitle());
            orderList.setPicUrl(product.getMainImage());
            productSell = productSellService.selectById(productOrder.getProductsellId());
            if(null != productSell){
                orderList.setStratDate(productSell.getStartDate());
            }
            orderLists.add(orderList);
        }
        return orderLists;
    }
}
