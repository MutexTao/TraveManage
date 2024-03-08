package com.halfsummer.controller.backend;


import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Theme;
import com.halfsummer.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *save(Theme theme) - 该方法处理保存主题的请求，它使用HTTP POST请求将Theme对象传递到服务器并将其插入到数据库中。该方法返回一个ServerResponse对象，其中包含操作的结果。
 *
 * update(String tId, Theme theme) - 该方法处理更新主题的请求，它使用HTTP POST请求将Theme对象传递到服务器并将其更新到数据库中。在更新之前，该方法通过tId参数将主题ID设置到传入的Theme对象中。该方法返回一个ServerResponse对象，其中包含操作的结果。
 *
 * delete(String tId) - 该方法处理删除主题的请求，它使用HTTP POST请求将Theme对象传递到服务器并将其从数据库中删除。在删除之前，该方法通过tId参数将主题ID设置到Theme对象中。该方法返回一个ServerResponse对象，其中包含操作的结果。
 *
 * themeView(String id, Model model) - 该方法处理获取主题详细信息的请求，它使用HTTP GET请求将Theme对象的ID传递到服务器。该方法使用该ID从数据库中检索主题详细信息，并将其添加到Model对象中，然后返回一个视图名称。
 *
 * addView() - 该方法返回一个视图名称，用于添加主题的请求。
 *
 * test() - 该方法处理获取按主题名称排序的主题列表的请求，它使用HTTP GET请求从数据库中检索主题列表，并返回一个包含主题列表的List对象。
 */
@Controller
@RequestMapping("/manage/theme")
public class ThemeManageController {

    @Autowired
    private IThemeService themeService;


    @ResponseBody
    @RequestMapping("/save")
    public ServerResponse save(Theme theme){
        return  ServerResponse.createByResult(theme.insert());
    }

    @ResponseBody
    @RequestMapping("/update/{tId}")
    public ServerResponse update(@PathVariable String tId, Theme theme){
        theme.setId(tId);
        return ServerResponse.createByResult(theme.updateById());
    }

    @ResponseBody
    @RequestMapping("/delete/{tId}")
    public ServerResponse delete(@PathVariable String tId){
        Theme theme=new Theme();
        theme.setId(tId);
        return ServerResponse.createByResult(theme.deleteById());
    }

    @RequestMapping("/detailView/{id}")
    public String themeView(@PathVariable String id, Model model){
        Theme theme=new Theme();
        theme.setId(id);
        model.addAttribute("theme",theme.selectById());
        return "backend/theme_add";
    }

    @RequestMapping("/addView")
    public String addView(){
        return "backend/theme_add";
    }



    @RequestMapping("/test")
    @ResponseBody
    public List test(){
        return themeService.listorderBythemeName();
    }


}

