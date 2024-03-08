package com.halfsummer.controller;


import com.halfsummer.entity.ThemeProduct;
import com.halfsummer.service.IThemeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/themeProduct")
public class ThemeProductController {

    @Autowired
    private IThemeProductService themeProductService;

    /**
     *
     * @param pid
     * @return
     *
     * 该控制器包含一个名为 selectByPid 的方法，它处理 GET 请求，使用 @RequestMapping("/list/{pid}") 注解进行映射。
     * 其中的 {pid} 表示一个路径参数，它将被绑定到方法的 pid 参数上。
     * 该方法通过调用 themeProductService.selectByPid 方法查询与 pid 相关联的主题产品，
     * 并将查询结果以 List<ThemeProduct> 的形式直接作为 HTTP 响应的内容返回给客户端。
     */
    @ResponseBody
    @RequestMapping("/list/{pid}")
    public List<ThemeProduct> selectByPid(@PathVariable String pid){
        return themeProductService.selectByPid(pid);
    }

}

