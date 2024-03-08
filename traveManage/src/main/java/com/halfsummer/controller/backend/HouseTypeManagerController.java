package com.halfsummer.controller.backend;

import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.HouseType;
import com.halfsummer.service.IHouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 在addView()方法中，返回一个字符串"backend/houseType_add"，表示将展示一个房屋类型添加的页面；
 * 在save()方法中，将传入的HouseType对象进行插入数据库操作，并将插入的结果封装到ServerResponse中返回；
 * 在delete()方法中，通过tId参数查询到对应的HouseType对象，然后将其从数据库中删除，并将删除的结果封装到ServerResponse中返回；
 * 在update()方法中，通过tId参数查询到对应的HouseType对象，然后将传入的houseType对象的属性值赋给查询到的对象，最后进行更新操作，并将更新的结果封装到ServerResponse中返回；
 * 在detailView()方法中，通过id参数查询到对应的HouseType对象，将其封装到Model中，并返回字符串"backend/houseType_add"，表示将展示一个房屋类型详情的页面。
 */
@Controller
@RequestMapping("/manage/houseType")
public class HouseTypeManagerController {

    @Autowired
    IHouseTypeService houseTypeServicel;


    @RequestMapping("/addView")
    public String addView(){
        return "backend/houseType_add";
    }

    @ResponseBody
    @RequestMapping("/save")
    public ServerResponse save(HouseType houseType){
        return  ServerResponse.createByResult(houseType. insert());
    }

    @ResponseBody
    @RequestMapping("/delete/{tId}")
    public ServerResponse delete(@PathVariable String tId){
        HouseType houseType=new HouseType();
        houseType.setId(tId);
        return ServerResponse.createByResult(houseType.deleteById());
    }


    @ResponseBody
    @RequestMapping("/update/{tId}")
    public ServerResponse update(@PathVariable String tId, HouseType houseType){
        houseType.setId(tId);
        return ServerResponse.createByResult(houseType.updateById());
    }

    @RequestMapping("/detailView/{id}")
    public String themeView(@PathVariable String id, Model model){
        HouseType houseType=new HouseType();
        houseType.setId(id);
        model.addAttribute("houseType",houseType.selectById());
        return "backend/houseType_add";
    }
}
