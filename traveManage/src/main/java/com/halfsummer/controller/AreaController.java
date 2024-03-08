package com.halfsummer.controller;


import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Area;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 */
@Controller
@RequestMapping("/area")
public class AreaController {

    @ResponseBody
    @RequestMapping("/list")
    public ServerResponse list(){
        Area area=new Area();
        return ServerResponse.createBySuccess(area.selectAll());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ServerResponse detail(@PathVariable Integer id){
        Area area=new Area();
        area.setAreaId(id);
        return ServerResponse.createBySuccess(area.selectById());
    }


}

