package com.halfsummer.controller;


import com.halfsummer.common.ServerResponse;
import com.halfsummer.service.IProductSellService;
import com.halfsummer.vo.PriceCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *第一个请求处理方法 detail() 用于处理 GET 请求 /productSell/{id}，其中的 {id} 是一个路径变量。
 * 该方法返回一个 ServerResponse 对象，其中包含了 id 对应的产品销售信息。
 * 第二个请求处理方法 getPriCal() 用于处理 GET 请求 /productSell/listpsVo/{pid}，其中的 {pid} 是一个路径变量。
 * 该方法返回一个 List<PriceCalendar> 对象，其中包含了 pid 对应的产品价格日历信息。
 */
@Controller
@RequestMapping("/productSell")
public class ProductSellController {

    @Autowired
    private IProductSellService productSellService;

    @ResponseBody
    @RequestMapping("/{id}")
    public ServerResponse detail(@PathVariable String id){
        return ServerResponse.createBySuccess(productSellService.selectById(id));
    }

    @ResponseBody
    @RequestMapping("/listpsVo/{pid}")
    public List<PriceCalendar> getPriCal(@PathVariable String pid){


        return productSellService.getPriCal(pid);

    }



}

