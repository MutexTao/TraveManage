package com.halfsummer.controller.backend;


import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductDesc;
import com.halfsummer.entity.ThemeProduct;
import com.halfsummer.service.IProductDescService;
import com.halfsummer.service.IProductService;
import com.halfsummer.service.IThemeProductService;
import com.halfsummer.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 *save：用于创建一个新产品，接受Product、ProductDesc和String[]类型的参数，并尝试将其保存到数据库中。方法返回一个ServerResponse类型的对象，该对象包含响应状态码、消息和可能的响应数据。
 *
 * update：用于更新现有产品，接受Product、ProductDesc和String[]类型的参数，并尝试将其保存到数据库中。该方法的第一个参数为路径变量pid，指定要更新的产品的ID。方法返回一个ServerResponse类型的对象，该对象包含响应状态码、消息和可能的响应数据。
 *
 * shelf：用于将指定ID的产品下架，返回一个ServerResponse类型的对象，该对象包含响应状态码、消息和可能的响应数据。
 *
 * delete：用于删除指定ID的产品及其相关的数据，返回一个ServerResponse类型的对象，该对象包含响应状态码、消息和可能的响应数据。
 *
 * deleteBatchIds：用于批量删除产品，接受一个String[]类型的参数pids，该参数包含要删除的产品的ID列表。方法返回一个ServerResponse类型的对象，该对象包含响应状态码、消息和可能的响应数据。
 *
 * addView：用于返回一个视图名称，该视图将用于呈现产品添加页面。此方法使用Model对象传递主题信息。
 *
 * listView：用于返回一个视图名称，该视图将用于呈现产品列表页面。
 *
 * updateView：用于返回一个视图名称，该视图将用于呈现产品更新页面。此方法使用Model对象传递产品、产品描述、主题和与产品关联的主题产品信息。
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManagerController {


    @Autowired
    private IProductService productService;
    @Autowired
    private IProductDescService productDescService;
    @Autowired
    private IThemeService themeService;
    @Autowired
    private IThemeProductService themeProductService;





    /**
     * 新增
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ServerResponse save(Product product, ProductDesc productDesc, String[] themeName){

        System.out.println(product.getPrice());



        ThemeProduct[] themeProducts=new ThemeProduct[themeName.length];
        for (int i=0;i<themeName.length;i++){
            String themeId=themeService.selectIdByName(themeName[i]);
            if (null==themeId){
                return ServerResponse.createByErrorMessage("不存在该主题");
            }
            themeProducts[i]=new ThemeProduct();
            themeProducts[i].setThemeId(themeId);
            themeProducts[i].setThemeName(themeName[i]);
        }



        try {
            productService.create(product,productDesc,themeProducts);
        } catch (Exception e) {
            return ServerResponse.createByError();
        }

        return ServerResponse.createBySuccess();
    }

    @RequestMapping("/update/{pid}")
    @ResponseBody
    public ServerResponse update(@PathVariable String pid,Product product, ProductDesc productDesc, String[] themeName){

        product.setPid(pid);
        ThemeProduct[] themeProducts=new ThemeProduct[themeName.length];
        for (int i=0;i<themeName.length;i++){
            String themeId=themeService.selectIdByName(themeName[i]);
            if (null==themeId){
                return ServerResponse.createByErrorMessage("不存在该主题");
            }
            themeProducts[i]=new ThemeProduct();
            themeProducts[i].setThemeId(themeId);
            themeProducts[i].setThemeName(themeName[i]);
        }

        try {
            productService.update(product,productDesc,themeProducts);
        } catch (Exception e) {
            return ServerResponse.createByError();
        }

        return ServerResponse.createBySuccess();
    }



    /**
     * 下架
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("/shelf/{pid}")
    public ServerResponse shelf(@PathVariable  String pid){
        Product product=new Product();
        product.setPid(pid);
        product.setStatus(2);//下架
        return ServerResponse.createByResult(product.updateById());
    }

    /**
     * 删除
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{pid}")
    public ServerResponse delete(@PathVariable  String pid){

        productService.cascadeDeleteById(pid);
        return ServerResponse.createBySuccess();
    }

    /**
     * 批量删除
     * @param pids
     * @return
     */

    @ResponseBody
    @RequestMapping("/deleteBatchIds")
    public ServerResponse deleteBatchIds(String[] pids){
        if (pids.length>60){
            return ServerResponse.createByErrorMessage("超出一次删除的记录");
        }
        productService.deleteBatchIds(pids);
        return ServerResponse.createBySuccess();
    }


    private boolean setThemeProduct(String[] themeName,ThemeProduct[] themeProducts){
        themeProducts=new ThemeProduct[themeName.length];
        for (int i=0;i<themeName.length;i++){
            String themeId=themeService.selectIdByName(themeName[i]);
            if (null==themeId){
                return false;
            }
            themeProducts[i]=new ThemeProduct();
            themeProducts[i].setThemeId(themeId);
            themeProducts[i].setThemeName(themeName[i]);
        }
        return true;
    }


    @RequestMapping("/addView")
    public String addView(Model model){

        model.addAttribute("theme",themeService.selectList(null));
        return "backend/product_add";
    }

    @RequestMapping("/listView")
    public String listView(){
        return "backend/product_list";
    }

    @RequestMapping("/updateView/{pid}")
    public String updateView(@PathVariable String pid, Model model){
        Product product=productService.selectById(pid);
        ProductDesc productDesc=productDescService.selectById(pid);

        model.addAttribute("product",product);
        model.addAttribute("productDesc",productDesc);
        model.addAttribute("theme",themeService.selectList(null));
        model.addAttribute("tp",themeProductService.selectByPid(pid));
//        model.addAttribute("themeProduct",themeProductService.selectByPid(product.getPid()));
        //复选框的值先缺着
        return "backend/product_update";
    }

    /**
     * 检查日期输入合法性
     * @return
     */
    private boolean checkDateInputIllegal(Date stratDate,Date endDate){
        //计算天数
        int  days = (int)(stratDate.getTime()-endDate.getTime())/ (1000*3600*24);
        if (days<0||days>60){
            return false;
        }
        return true;
    }

}

