package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.Theme;

import java.util.List;
import java.util.Map;

/**
 * 主题 服务类
 */
public interface IThemeService extends IService<Theme> {

    public String selectIdByName(String themeName);

    public List<Map<String,String>> listorderBythemeName();

}
