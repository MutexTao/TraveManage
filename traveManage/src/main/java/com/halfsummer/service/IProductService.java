package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductDesc;
import com.halfsummer.entity.ThemeProduct;

import java.util.List;

/**
 * 产品 服务类
 */
public interface IProductService extends IService<Product> {

    public ServerResponse create(Product product, ProductDesc productDesc, ThemeProduct[] themeProducts) throws Exception;

    public void cascadeDeleteById(String pid);

    public void deleteBatchIds(String[] pids);

    public void update(Product product, ProductDesc productDesc, ThemeProduct[] themeProducts) throws Exception;

    public List<Product> getIndexproduct(int size);

    public List<Product> hotProduct(int size);

    public List<Product> getAllProduct();


}
