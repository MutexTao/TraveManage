package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.dao.ProductDescMapper;
import com.halfsummer.dao.ProductMapper;
import com.halfsummer.dao.ThemeProductMapper;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductDesc;
import com.halfsummer.entity.ThemeProduct;
import com.halfsummer.entity.User;
import com.halfsummer.service.IProductService;
import com.halfsummer.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDescMapper productDescMapper;
    @Autowired
    private ThemeProductMapper themeProductMapper;



    /**
     * 创建一个产品，同时将生成产品描述(产品表切割),主题产品（中间表）
     * @param product
     * @param productDesc
     * @param  themeProducts
     * @return
     */
    @Override
    public ServerResponse create(Product product, ProductDesc productDesc, ThemeProduct[] themeProducts) throws Exception {
        //生成产品Id
        String productId=Long.toString(IDUtils.genItemId());
        product.setPid(productId);
        product.setCreateTime(new Date());

        //设置产品描述的产品id
        productDesc.setPid(productId);

        //设置主题产品的id
        List<ThemeProduct> tpList=new ArrayList();
        for (int i=0;i<themeProducts.length;i++){
            themeProducts[i].setProductId(productId);
            tpList.add(themeProducts[i]);
        }

        /********持久化到数据库****************/
        //保存
        for (int i=0;i<themeProducts.length;i++){

            if (themeProductMapper.insert(themeProducts[i])<=0){
                throw new Exception();
            }
        }

        if (productDescMapper.insert(productDesc)<=0){
            throw new Exception();
        }
        if (productMapper.insert(product)<=0){
            throw  new Exception();
        }
        return ServerResponse.createBySuccess();
    }

    /**
     * 删除
     * @param pid
     */
    public void cascadeDeleteById(String pid){

        themeProductMapper.delete(new EntityWrapper<ThemeProduct>().eq("product_id",pid));
        productDescMapper.deleteById(pid);
        productMapper.deleteById(pid);
    }

    /**
     * 批量删除
     * @param pids
     */
    @Override
    public void deleteBatchIds(String[] pids) {
        for (int i=0;i<pids.length;i++){
            cascadeDeleteById(pids[i]);
        }
    }

    /**
     * 更新
     * @param product
     * @param productDesc
     * @param themeProducts
     * @return
     * @throws Exception
     */
    public void update(Product product, ProductDesc productDesc, ThemeProduct[] themeProducts) throws Exception {

        String productId=product.getPid();
        product.setUpdateTime(new Date());
        //设置产品描述的产品id
        productDesc.setPid(productId);

        //设置主题产品的id
        List<ThemeProduct> tpList=new ArrayList();
        for (int i=0;i<themeProducts.length;i++){
            themeProducts[i].setProductId(productId);
            tpList.add(themeProducts[i]);
        }

        /********持久化到数据库****************/



        //先删除后插入
        if (themeProducts.length>0){
            themeProductMapper.delete(new EntityWrapper<ThemeProduct>().eq("product_id",themeProducts[0].getProductId()));
        }
        for (int i=0;i<themeProducts.length;i++){
            if (themeProductMapper.insert(themeProducts[i])<=0){
                throw  new Exception();
            }
        }
        if (productDescMapper.updateById(productDesc)<=0){
            throw  new Exception();
        }
        if (productMapper.updateById(product)<=0){
            throw new Exception();
        }




    }

    /**
     *
     * @param size
     * @return
     *
     * 这段代码实现了获取商品列表的功能。首先，通过Mybatis Plus提供的EntityWrapper构造查询条件，
     * 使查询结果按照商品的创建时间倒序排序，并且查询结果中只包含状态为1的商品。
     * 然后，通过调用selectList方法获取所有满足条件的商品列表，再通过调用subList方法截取前size个商品作为返回结果。
     */
    @Override
    public List<Product> getIndexproduct(int size) {
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.orderBy("create_time",false);
        entityWrapper.eq("status",1);
        List<Product> productList=selectList(entityWrapper);
        return productList.subList(0,size);
    }

    /**
     *
     * @param size
     * @return
     *
     * 这段代码实现了查询热门商品的功能。它首先创建了一个 EntityWrapper 对象，
     * 并按照 sell 字段倒序排序。然后，它使用 last 方法在 SQL 语句的末尾添加一个 limit 子句，
     * 指定要返回的结果集的大小。最后，它调用 selectList 方法从数据库中获取满足条件的商品数据，并将其返回给调用方。
     */
    @Override
    public List<Product> hotProduct(int size) {
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.orderBy("sell",false);
        String limt="limit "+size;
        entityWrapper.last(limt);
        return selectList(entityWrapper);
    }

    /**
     *
     * @return
     *
     * 首先创建了一个 EntityWrapper 对象，该对象是 MyBatis-Plus 中的一个查询构造器，用于构建 SQL 查询语句。
     * 接下来，定义了一个空的 List<Product> 对象 productList。然后，使用 baseMapper（继承自 MybatisPlusBaseServiceImpl）
     * 调用 selectList 方法查询数据库中的 Product 数据，并将结果保存到 productList 中。
     * 最后，返回 productList。该方法的作用是获取数据库中所有的 Product 记录。
     */
    @Override
    public List<Product> getAllProduct() {
        EntityWrapper entityWrapper=new EntityWrapper();
        List<Product> productList = new ArrayList<>();
        productList=this.baseMapper.selectList(entityWrapper);
        return productList;
    }


}
