package com.halfsummer.controller.backend;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.Const;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.User;
import com.halfsummer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 *list()方法用于获取用户列表，该方法使用@RequestParam注解将current和size参数标记为可选参数，默认值分别为1和10，它们指定要返回的用户列表的页数和大小。此方法通过使用EntityWrapper<User>类创建一个查询条件，将用户列表分页并使用selectPage()方法进行查询，然后将结果封装到ServerResponse对象中返回。@ResponseBody注解表示响应体将包含返回的对象。
 *
 * getCountPage()方法用于获取用户数并返回。该方法使用selectCount(null)方法查询所有用户的数量，然后将结果转换为int类型并返回。
 *
 * delete()方法用于批量删除用户。该方法接收一个id数组作为参数，使用userService.deleteBatchIds()方法批量删除用户，将结果封装到ServerResponse对象中返回。@ResponseBody注解表示响应体将包含返回的对象。
 *
 * login()方法用于处理用户登录请求。该方法接收username和password作为参数，使用userService.login()方法进行用户验证并返回ServerResponse对象。如果返回的响应表示成功，则将当前用户对象存储在当前会话中。
 *
 * update()方法用于更新用户信息。该方法接收一个User对象作为参数，并设置User对象的updateTime属性，然后使用updateById()方法将User对象保存到数据库。该方法将结果封装到ServerResponse对象中返回。@ResponseBody注解表示响应体将包含返回的对象。
 *
 * updateView()方法用于获取用户更新页面的视图。该方法接收一个id参数作为用户ID，使用selectById()方法获取用户对象并将其设置为模型属性。然后，该方法将视图名称作为字符串返回。
 *
 * loginView()方法用于获取用户登录页面的视图。该方法将视图名称作为字符串返回。
 */
@Controller
@RequestMapping("/manage/user")
public class UserMangeController {


    @Autowired
    private IUserService userService;


    /**
     * 用户列表
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServerResponse list(String keyword, @RequestParam(value="current",defaultValue="1") int current, @RequestParam(value="size",defaultValue="10") int size){
        System.out.println(keyword);
        EntityWrapper<User> userEntityWrapper=new EntityWrapper<User>();
        userEntityWrapper.like("username",keyword).or().like("email",keyword).or().like("phone",keyword);
        return ServerResponse.createBySuccess(userService.selectPage(new Page<User>(current,size),userEntityWrapper));
    }

    /**
     *
     */
    @ResponseBody
    @RequestMapping("/count")
    public int getCountPage(){
        return userService.selectCount(null);
    }

    /**
     * 批量删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public  ServerResponse delete(String[] id){
        return ServerResponse.createByResult(userService.deleteBatchIds(Arrays.asList(id)));
    }

    /**
     * 用户登录
     * @param  username
     * @param  password
     * @param  session
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){

        ServerResponse<User> response= userService.logion(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return  response;
    }

    /**
     * 更新
     * @param user
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ServerResponse update(User user){
        user.setUpdateTime(new Date());
        return ServerResponse.createByResult(user.updateById());

    }

    @RequestMapping("/updateView/{id}")
    public String updateView(@PathVariable String id, Model model){
        User user=userService.selectById(id);
        model.addAttribute(user);
        return "backend/user_update";
    }

    @RequestMapping("/loginView")
    public String loginView(){
        return "backedn/login";
    }
}

