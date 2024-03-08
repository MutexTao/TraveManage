package com.halfsummer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.halfsummer.entity.Theme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *  主题Mapper 接口
 */
@Mapper
public interface ThemeMapper extends BaseMapper<Theme> {


    public List<Map<String,String>> listorderBythemeName();

}
