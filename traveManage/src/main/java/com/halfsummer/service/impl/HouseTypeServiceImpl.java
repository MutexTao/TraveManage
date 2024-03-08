package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.HouseTypeMapper;
import com.halfsummer.entity.HouseType;
import com.halfsummer.service.IHouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseTypeServiceImpl extends ServiceImpl<HouseTypeMapper, HouseType> implements IHouseTypeService {

    @Autowired
    HouseTypeMapper houseTypeMapper;
}
