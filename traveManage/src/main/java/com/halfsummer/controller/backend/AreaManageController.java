package com.halfsummer.controller.backend;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfsummer.common.ResponseCode;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Area;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 地区码表 前端控制器
 * 首先对参数进行检查，如果 areaName 或 level 属性为空，则返回一个带有错误码和错误信息的 ServerResponse 对象，提示参数不合法。
 *
 * 如果参数合法，则判断该区域是否已经存在于数据库中，如果已经存在，则返回一个带有错误信息的 ServerResponse 对象，提示该城市已经存在。
 *
 * 如果区域不存在，则将该区域插入到数据库中，返回一个带有操作结果的 ServerResponse 对象。
 */
@Controller
@RequestMapping("/manage/area")
public class AreaManageController {


    @ResponseBody
    @RequestMapping("/create")
    public ServerResponse create(Area area){
        if (StringUtils.isBlank(area.getAreaName())||null==area.getLevel()){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }else if (null!=area.selectOne(new EntityWrapper().eq("areaName",area.getAreaName()))){
            return ServerResponse.createByErrorMessage("城市已存在");
        }
        return ServerResponse.createByResult(area.insert());
    }




}

