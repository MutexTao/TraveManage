package com.halfsummer.controller.backend;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.ResponseCode;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.HouseType;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductSell;
import com.halfsummer.service.IHouseTypeService;
import com.halfsummer.service.IProductSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *list方法：查询产品销售列表，并通过参数current和size指定查询结果的起始位置和数量。
 *
 * listBypid方法：通过产品ID查询对应的产品销售列表，并同样使用current和size参数进行分页查询。
 *
 * create方法：新增一个产品销售，并通过参数ProductSell指定新增的产品销售信息。
 *
 * update方法：更新指定ID的产品销售信息，并通过参数id和ProductSell指定需要更新的产品销售信息。
 *
 * delete方法：删除指定ID的产品销售信息，并通过参数id指定需要删除的产品销售信息。
 *
 * delete方法：批量删除多个产品销售信息，并通过参数ids指定需要删除的产品销售信息。
 *
 * insertBatch方法：批量新增多个产品销售信息，并通过参数productSells和days指定需要新增的产品销售信息以及持续的天数。
 *
 * addView方法：用于显示新增产品销售的页面，并通过参数pid、title、price、model指定需要显示的产品ID、标题、价格、以及模型信息。
 *
 * listView方法：用于显示产品销售列表的页面，并通过参数pid和model指定需要显示的产品ID和模型信息。
 *
 * updateView方法：用于显示更新产品销售信息的页面，并通过参数id和model指定需要显示的产品销售ID和模型信息。
 */
@Controller
@RequestMapping("/manage/productSell")
public class ProductSellManageController {

    @Autowired
    private IProductSellService productSellService;

    @Autowired
    private IHouseTypeService houseTypeService;

    /**
     * 列表
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){

        if (current<0||size<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        return ServerResponse.createBySuccess(productSellService.selectPage(new Page(current,size))) ;
    }

    /**
     * 列表
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/list/{pid}")
    @ResponseBody
    public ServerResponse listBypid(@PathVariable String pid,@RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){

        if (current<0||size<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        EntityWrapper<ProductSell> entityWrapper=new EntityWrapper();
        entityWrapper.eq("pid",pid);
        entityWrapper.orderBy("start_date",true);
        return ServerResponse.createBySuccess(productSellService.selectPage(new Page(current,size),entityWrapper)) ;
    }

    /**
     * 新增
     * @param productSell
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ServerResponse create(ProductSell productSell){
        productSell.setCreateTime(new Date());
        return ServerResponse.createBySuccess(productSell.insert());
    }

    /**
     *修改
     * @param id
     * @param productSell
     * @return
     */
    @ResponseBody
    @RequestMapping("/update/{id}")
    public ServerResponse update(@PathVariable  String id, ProductSell productSell){
        productSell.setId(id);
        productSell.setUpdateTime(new Date());
        return ServerResponse.createByResult(productSell.updateById());
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete/{id}")
    public  ServerResponse delete(@PathVariable String id){
        ProductSell productSell=new ProductSell();
        productSell.setId(id);
        return ServerResponse.createByResult(productSell.deleteById());
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public  ServerResponse delete(String[] ids){
        return ServerResponse.createByResult(productSellService.deleteBatchIds(Arrays.asList(ids)));
    }

    /**
     * 批量新增
     * @param productSells
     * @return
     */
    @RequestMapping("/insertBatch")
    @ResponseBody
    public ServerResponse insertBatch(ProductSell productSells,int days){
        if (days>30){
            return ServerResponse.createByErrorMessage("不能连续设置超过30天的产品销售");
        }
        ArrayList<ProductSell> productSellArrayList=new ArrayList<>();
        for (int i=0;i<=days-1;i++){
            ProductSell productSell=new ProductSell(productSells);
            Calendar c = Calendar.getInstance();
            c.setTime(productSell.getStartDate());
            c.add(Calendar.DAY_OF_MONTH, i);// +i天

            productSell.setStartDate(c.getTime());
            productSell.setCreateTime(new Date());
            productSell.setHouseTypeName(productSells.getHouseTypeName());
            productSellArrayList.add(productSell);
        }

        return ServerResponse.createByResult(productSellService.insertBatch(productSellArrayList));
    }

    @RequestMapping("/addView")
    public String addView(String pid, String title,Double price, Model model) throws UnsupportedEncodingException {
//        String param = new String(title.getBytes("ISO8859-1"), "UTF-8");//解决get乱码
        model.addAttribute("pid",pid);
        model.addAttribute("title",title);
        model.addAttribute("price",price);

        EntityWrapper<HouseType> entityWrapper = new EntityWrapper<>();
        List<HouseType> houseTypeList = houseTypeService.selectList(entityWrapper);
        String houseType = "";
        for(int i = 0;i<houseTypeList.size();i++){
                houseType+="<option value='"+houseTypeList.get(i).getHouseTypeName()+"'>"+houseTypeList.get(i).getHouseTypeName()+"</option>";
        }
        model.addAttribute("houseType",houseType);
        return "backend/productSell_add";
    }

    @RequestMapping("/listView/{pid}")
    public String listView(@PathVariable String pid,Model model){
        model.addAttribute("pid",pid);
        return "backend/productSell_list";
    }

    @RequestMapping("/updateView/{id}")
    public String updateView(@PathVariable String id,Model model){
        ProductSell productSell = productSellService.selectById(id);
        model.addAttribute("productSell", productSell);
        EntityWrapper<HouseType> entityWrapper = new EntityWrapper<>();
        List<HouseType> houseTypeList = houseTypeService.selectList(entityWrapper);
        String houseType = "";
        for(int i = 0;i<houseTypeList.size();i++){
            if (productSell.getHouseTypeName().equals(houseTypeList.get(i).getHouseTypeName())){
                houseType+="<option selected='selected' value='"+houseTypeList.get(i).getHouseTypeName()+"'>"+houseTypeList.get(i).getHouseTypeName()+"</option>";
            }else {
                houseType+="<option value='"+houseTypeList.get(i).getHouseTypeName()+"'>"+houseTypeList.get(i).getHouseTypeName()+"</option>";
            }
        }
        model.addAttribute("houseType",houseType);
        return "backend/productSell_update";
    }
}

