package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.ThemeMapper;
import com.halfsummer.entity.Theme;
import com.halfsummer.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  服务实现类
 */
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, Theme> implements IThemeService {

    @Autowired
    private ThemeMapper themeMapper;

    /**
     *
     * @param themeName
     * @return
     *
     * 这段代码实现了根据主题名称查询主题的功能。代码首先创建了一个EntityWrapper对象，用于构建查询条件。
     * 然后使用该EntityWrapper对象调用selectOne方法，从数据库中查询符合条件的Theme对象。
     * 如果查询结果为null，则返回null。如果查询结果不为null，则返回查询到的Theme对象的id属性值。
     */
    @Override
    public String selectIdByName(String themeName) {
//        Theme theme=new Theme();
//        theme.setThemeName(themeName);
//        theme=baseMapper.selectOne(theme);
        EntityWrapper<Theme> entityWrapper=new EntityWrapper();
        Theme theme=selectOne(entityWrapper.eq("theme_name",themeName));
        if (null==theme){
            return null;
        }
        return theme.getId();
    }

    @Override
    public List<Map<String, String>> listorderBythemeName() {
        return themeMapper.listorderBythemeName();
    }
}
