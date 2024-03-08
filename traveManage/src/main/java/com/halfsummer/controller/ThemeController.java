package com.halfsummer.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.ResponseCode;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.Theme;
import com.halfsummer.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private IThemeService themeService;

    /**
     * @param tid
     * @return
     *
     * 这段代码定义了一个 detail 方法，使用了 @PathVariable 注解来获取 URL 中的参数 tid。然后它创建一个新的 Theme 实例，
     * 并将 tid 分配给该实例的 id 属性。最后，它调用 selectById() 方法来从数据库中获取指定 tid 的 Theme 对象，
     * 将其作为成功响应的内容封装到一个 ServerResponse 对象中，并返回该对象。
     * 因此，这段代码的作用是根据给定的 tid 获取对应的 Theme 对象。
     */
    @ResponseBody
    @RequestMapping("/{tid}")
    public ServerResponse detail(@PathVariable String tid){
        Theme theme=new Theme();
        theme.setId(tid);
        return ServerResponse.createBySuccess(theme.selectById());
    }

    /**
     *
     * @param current
     * @param size
     * @return
     *
     * 该方法通过接收两个请求参数 current 和 size 来获取主题列表，
     * 其中 current 参数表示当前页数，默认值为1，size 参数表示每页显示的记录数，默认值为10。
     * 方法会根据这两个参数查询数据库，并将查询结果封装在 ServerResponse 对象中返回。
     * 在方法体中，先通过 System.out.println 打印出当前页数和每页显示的记录数。
     * 然后对这两个参数进行简单的合法性校验，即如果参数值小于0，则返回一个带有错误码和错误信息的 ServerResponse 对象。
     * 最后，通过调用 themeService.selectPage 方法获取主题列表，并将结果封装在 ServerResponse 对象中返回。
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(value="current",defaultValue="1") int current,@RequestParam(value="size",defaultValue="10") int size){
        System.out.println(current+"--"+size);
        if (current<0||size<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        return ServerResponse.createBySuccess(themeService.selectPage(new Page(current,size))) ;
    }


}

