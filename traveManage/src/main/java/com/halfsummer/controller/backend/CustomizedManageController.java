package com.halfsummer.controller.backend;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.ResponseCode;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Customization;
import com.halfsummer.service.ICustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Controller
@RequestMapping("/manage/customization")
public class CustomizedManageController {

    @Autowired
    private ICustomizationService customizationService;

    /**
     *查看详情
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ServerResponse<Customization> detail(@PathVariable String id){
        Customization customization=new Customization();
        customization.setId(id);
        return ServerResponse.createBySuccess(customization.selectById()) ;
    }

    @RequestMapping("/detailView/{id}")
    public String detailView(@PathVariable String id, Model model){
        Customization customization=new Customization();
        customization.setId(id);
        model.addAttribute("customization",customization.selectById());
        return "backend/customization";
    }

    /**
     * 设置状态
     */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public ServerResponse updateStauts(@PathVariable String id, String status){
        Customization customization=new Customization();
        customization.setStatus(Integer.parseInt(status));
        customization.setId(id);
        return ServerResponse.createByResult(customization.updateById());
    }


    /**
     * 设置管理员回复
     */
    @RequestMapping("/updateAdminReply/{id}")
    @ResponseBody
    public ServerResponse updateAdminReply(@PathVariable String id, String adminReply){
        Customization customization=new Customization();
        customization.setId(id);
        customization.setAdminReply(adminReply);
        return ServerResponse.createByResult(customization.updateById());
    }

    /**
    * 分页查看
    */

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse list(String status, @RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){
        if (current<=0||size<=0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        EntityWrapper entityWrapper=null;
        if (status!=null&&!"".equals(status)){
            entityWrapper=new EntityWrapper();
            entityWrapper.eq("status",Integer.parseInt(status));
        }

        return ServerResponse.createBySuccess(customizationService.selectMapsPage(new Page(current,size),entityWrapper)) ;
    }

}
