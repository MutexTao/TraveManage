package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.ProductSell;
import com.halfsummer.vo.PriceCalendar;

import java.util.List;

/**
 * 产品销售 服务类
 */
public interface IProductSellService extends IService<ProductSell> {

    public List<PriceCalendar> getPriCal(String pid);

    List<PriceCalendar> getPriCalByHouseTypeName(String pid, String houseTypeName);
}
