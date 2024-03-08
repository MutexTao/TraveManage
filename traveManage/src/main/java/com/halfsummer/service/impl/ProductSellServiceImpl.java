package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.ProductSellMapper;
import com.halfsummer.entity.ProductSell;
import com.halfsummer.service.IProductSellService;
import com.halfsummer.vo.PriceCalendar;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *  服务实现类
 */
@Service
public class ProductSellServiceImpl extends ServiceImpl<ProductSellMapper, ProductSell> implements IProductSellService {

    @Override
    public List<PriceCalendar> getPriCal(String pid) {
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //创建查询条件并添加相等条件和大于条件
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("pid",pid);
        //entityWrapper.lt("start_date",sdf.format(new Date()));
        entityWrapper.gt("stock",0);
        //将查询结果排序
        entityWrapper.orderBy("start_date",true);
        List<ProductSell> productSellList=selectList(entityWrapper);
        System.out.println("before splicing:"+productSellList);
        return splicing(productSellList);
    }

    public List<PriceCalendar> getPriCalByHouseTypeName(String pid,String houseTypeName) {
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //创建查询条件并添加相等条件和大于条件
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("pid",pid);
        //entityWrapper.lt("start_date",sdf.format(new Date()));
        entityWrapper.gt("stock",0);
        //排序
        entityWrapper.orderBy("start_date",true);
        List<ProductSell> productSellList=selectList(entityWrapper);
        System.out.println("before splicing:"+productSellList);
        return splicingByHouseTypeName(productSellList,houseTypeName);
    }

    //拼装返回vo

    /**
     *
     * @param productSellList
     * @return
     *
     * 方法中首先创建了一个空的 List<PriceCalendar> 对象 priceCalendars，然后遍历 productSellList 列表，
     * 对于每个 ProductSell 对象，创建一个新的 PriceCalendar 对象，并将其加入到 priceCalendars 列表中。
     * 创建 PriceCalendar 对象时，将 ProductSell 对象的 startDate、pPrice、id、houseTypeName 属性作为参数传入。
     */
    private List<PriceCalendar> splicing(List<ProductSell> productSellList){
        List<PriceCalendar> priceCalendars=new ArrayList<>();
        for (ProductSell productSell:productSellList){
            PriceCalendar priceCalendar=new PriceCalendar(productSell.getStartDate(),productSell.getpPrice(),productSell.getId(),productSell.getHouseTypeName());
            priceCalendars.add(priceCalendar);
        }
        System.out.println("after splicing:"+priceCalendars);
        return priceCalendars;
    }

    /**
     *
     * @param productSellList
     * @param houseTypeName
     * @return
     *
     * 这个方法会遍历 productSellList 集合中的每个 ProductSell 对象，如
     * 果当前对象的 houseTypeName 属性等于传入的 houseTypeName 或者 houseTypeName 为空字符串，
     * 则将当前对象转换为 PriceCalendar 对象并添加到 priceCalendars 集合中。
     * 最后，将 priceCalendars 集合返回。
     */
    private List<PriceCalendar> splicingByHouseTypeName(List<ProductSell> productSellList,String houseTypeName){
        List<PriceCalendar> priceCalendars=new ArrayList<>();
        for (ProductSell productSell:productSellList){
            if (houseTypeName.equals(productSell.getHouseTypeName()) || houseTypeName.equals("")){
                PriceCalendar priceCalendar=new PriceCalendar(productSell.getStartDate(),productSell.getpPrice(),productSell.getId(),productSell.getHouseTypeName());
                priceCalendars.add(priceCalendar);
            }
        }
        System.out.println("after splicing:"+priceCalendars);
        return priceCalendars;
    }

}
