package com.halfsummer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.ResponseCode;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.service.IHouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这段代码定义了一个HouseTypeController类，其中包含了一个list方法。
 * 该方法使用@RequestParam注解获取current和size参数，并且默认值分别为1和10。方法的作用是通过调用houseTypeService的selectPage方法，
 * 获取当前页码和每页大小的房屋类型信息，并通过ServerResponse对象返回查询结果。在current或size小于0时，返回错误信息ResponseCode.ILLEGAL_ARGUMENT。
 */
@Controller
@RequestMapping("/houseType")
public class HouseTypeController {

    @Autowired
    IHouseTypeService houseTypeServicel;

    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){
        System.out.println(current+"--"+size);
        if (current<0||size<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        return ServerResponse.createBySuccess(houseTypeServicel.selectPage(new Page(current,size))) ;
    }
}
