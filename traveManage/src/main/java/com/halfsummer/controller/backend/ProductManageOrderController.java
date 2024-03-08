package com.halfsummer.controller.backend;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.ProductOrder;
import com.halfsummer.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *list()方法映射到/list路径，用于获取商品订单列表。keyword参数表示关键字，current参数表示当前页码，size参数表示每页记录数。该方法首先根据关键字生成一个查询条件entityWrapper，然后使用该条件和分页参数调用productOrderService的selectPage()方法进行查询，最后将查询结果封装成ServerResponse对象返回。
 * detailView()方法映射到/detailView/{id}路径，用于查看指定ID的订单详情。该方法通过调用productOrderService的selectById()方法获取订单对象，并将其封装到Model对象中，最后返回backend/order_detail视图。
 * listView()方法映射到/listView路径，用于显示订单列表视图。该方法返回backend/order_list视图。
 */
@Controller
@RequestMapping("/manage/productOrder")
public class ProductManageOrderController {

    //自动注入bean对象，在需要引用其他类的时候使用
    @Autowired
    private IProductOrderService productOrderService;

    @RequestMapping("/list")
    //将注释结果转为JSON格式
    @ResponseBody
    public ServerResponse list(String keyword, @RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){
        EntityWrapper entityWrapper=new EntityWrapper();
        if (keyword!=null&!"".equals(keyword)){
            entityWrapper.eq("order_no",keyword).or().eq("username",keyword);
            entityWrapper.orderBy("create_time",false);
        }

        return ServerResponse.createBySuccess(productOrderService.selectPage(new Page<ProductOrder>(current,size),entityWrapper));
    }

    @RequestMapping("/detailView/{id}")
    public String detailView(@PathVariable String id, Model model){
        ProductOrder productOrder=productOrderService.selectById(id);
        model.addAttribute("order",productOrder);
        return "backend/order_detail";
    }

    @RequestMapping("/listView")
    public String listView(){
        return "backend/order_list";
    }



}

