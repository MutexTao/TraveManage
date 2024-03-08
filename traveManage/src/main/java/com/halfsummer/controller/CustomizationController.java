package com.halfsummer.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfsummer.common.Const;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Customization;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.User;
import com.halfsummer.entity.UserAction;
import com.halfsummer.service.ICustomizationService;
import com.halfsummer.service.IProductService;
import com.halfsummer.service.IThemeProductService;
import com.halfsummer.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *save：该接口用于保存用户的个性化定制信息，包括用户id、目的地、状态等信息。首先获取目的地，然后根据目的地查询符合条件的商品列表。接着遍历商品列表，对于每一个商品，查询该用户是否已经对该商品进行过评分。如果没有，则在 userAction 数据表中插入一条该商品对应的用户行为；否则，在原有的评分基础上增加5分，然后更新该用户行为。最后将用户的个性化定制信息插入到 customization 数据表中，并返回结果。
 * listView：该视图用于显示当前用户所有的个性化定制信息。
 * detailView/{id}：该视图用于显示某个具体的个性化定制信息。
 * 其中， ICustomizationService、IProductService 和 IUserActionService 都是服务层接口，分别用于操作 customization、product 和 userAction 表。
 */
@Controller
@RequestMapping("/customization")
public class CustomizationController {

    @Autowired
    private ICustomizationService customizationService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserActionService userActionService;


    @PostMapping("/save")
    @ResponseBody
    public ServerResponse<String> save(Customization customization, HttpSession session) throws IOException {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("请登录");
        }
        customization.setUid(user.getId());
        customization.setStatus(0);
        System.out.println(customization.toString());

        //获取目的地
        String endAreaname=customization.getEndAreaname();
        //查询目的地符合的商品
        EntityWrapper<Product> productEntityWrapper = new EntityWrapper<>();
        productEntityWrapper.eq("end_areaName",endAreaname);
        List<Product> productList = productService.selectList(productEntityWrapper);
        //定义一个userAction用来存储
        UserAction userAction = new UserAction();
        userAction.setUserid(user.getId());
        for (int i = 0 ;i<productList.size();i++){
            String pid = productList.get(i).getPid();
            userAction.setProductid(pid);
            EntityWrapper<UserAction> userActionEntityWrapper = new EntityWrapper<>();
            userActionEntityWrapper.eq("userid",user.getId());
            userActionEntityWrapper.eq("productid",pid);
            UserAction ua=userActionService.selectOne(userActionEntityWrapper);
            if (ua==null){
                //数据库没有存储该数据，则插入一条
                userAction.setScore(5);
                userActionService.insert(userAction);

            }else {
                Integer score = ua.getScore();
                userAction.setScore(score+5);
                userActionService.update(userAction,userActionEntityWrapper);
            }
        }
        return ServerResponse.createByResult(customization.insert());
    }


    @RequestMapping("listView")
    public String listView(HttpSession session, Model model){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return "index/index";
        }
        List<Customization> customizations=customizationService.selectListByUid(user.getId());
        model.addAttribute("customizations",customizations);
        model.addAttribute("user",user);
        return "index/cuslist";

    }

    @RequestMapping("/detailView/{id}")
    public String detailView(@PathVariable String id, Model model){
        Customization customization=customizationService.selectById(id);
        model.addAttribute("cus",customization);
        return "index/cus_detail";
    }

}

