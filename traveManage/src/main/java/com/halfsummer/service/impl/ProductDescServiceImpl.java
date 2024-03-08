package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.ProductDescMapper;
import com.halfsummer.entity.ProductDesc;
import com.halfsummer.service.IProductDescService;
import org.springframework.stereotype.Service;

/**
 *  产品描述 服务实现类
 */
@Service
public class ProductDescServiceImpl extends ServiceImpl<ProductDescMapper, ProductDesc> implements IProductDescService {



}
