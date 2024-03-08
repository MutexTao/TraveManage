package com.halfsummer.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfsummer.common.Const;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.EmailValidate;
import com.halfsummer.entity.User;
import com.halfsummer.service.IEmailValidateService;
import com.halfsummer.service.IUserService;
import com.halfsummer.controller.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailValidateService emailValidateService;

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ServerResponse<String> register (User user,String registerCode){
        System.out.println(user.toString());
        //对传参进行校验

        //验证邮箱验证码
        EntityWrapper<EmailValidate> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("email",user.getEmail())
                .eq("validate_code",registerCode);
        if(emailValidateService.selectCount(entityWrapper)<=0){
            return ServerResponse.createByErrorMessage("注册码错误");
        }

        //验证邮箱格式
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+");
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            return ServerResponse.createByErrorMessage("邮箱格式不正确");
        }

        //保存
        user.setCreateTime(new Date());
        user.setRole(10);
        if (user.insert()){
            return ServerResponse.createBySuccessMessage("注册成功");
        }else{
            return ServerResponse.createByError();
        }

    }


    /**
     * 用户登录
     * @param  username
     * @param  password
     * @param  session
     * @return
     *
     * 该方法接收三个参数：username、password 和 session，分别表示用户名、密码和 HttpSession 对象。
     * 该方法调用 userService 中的 logion 方法进行登录，返回一个 ServerResponse<User> 对象。
     * 如果登录成功，则将用户信息存储到 HttpSession 中，然后返回 ServerResponse<User> 对象。
     * 如果登录失败，则直接返回 ServerResponse<User> 对象。
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
     * 用户登录
     * @param  username
     * @param  password
     * @param  session
     * @return
     *
     * 首先调用userService的logion方法验证用户是否存在且密码是否正确，如果验证通过，则判断该用户是否为管理员用户。
     * 如果是管理员用户，则返回错误信息；
     * 否则将用户信息保存到session中，key为Const.ADMIN_USER，表示是管理员用户。最后返回验证结果。
     */
    @RequestMapping("/admin/login")
    @ResponseBody
    public ServerResponse<User> adminLogin(String username, String password, HttpSession session){

        ServerResponse<User> response= userService.logion(username,password);

        if (response.isSuccess()){
            if (response.getData().getRole()>=10){
                return ServerResponse.createByErrorMessage("请登录管理员用户");
            }
            session.setAttribute(Const.ADMIN_USER,response.getData());
        }
        return  response;
    }

    /**
     *
     * @param user
     * @return
     *
     * 该方法使用 userService 的 updateById 方法更新数据库中与 user 对象相关联的记录，
     * 并将操作结果封装在 ServerResponse 中返回。
     * 如果操作成功，则 ServerResponse 中的 isSuccess 属性为 true，否则为 false，同时也会附带一个操作结果的信息。
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(User user){
        return ServerResponse.createByResult(userService.updateById(user));
    }


    /**
     *
     * @param session
     * @param model
     * @return
     *
     * 首先从 HttpSession 中获取当前登录用户的信息，
     * 然后将用户信息添加到 Model 中，
     * 最后返回视图名为 "index/user_info" 的页面。
     * 这个页面将展示当前登录用户的信息，用户可以在页面上修改个人信息。
     */
    @RequestMapping("updateView")
    public String updateView(HttpSession session, Model model){
        User user = (User)session.getAttribute(Const.CURRENT_USER);

        model.addAttribute("user",user);
        return "index/user_info";
    }

    /**
     *
     * @param session
     * @return
     *
     * 退出登录
     */
    @RequestMapping("loginout")
    public String loginout(HttpSession session,Model model) throws Exception {
        session.removeAttribute(Const.CURRENT_USER);
//        IndexController indexController = new IndexController();
//        indexController.indexView(session,model);
        return "redirect:http://localhost:8088/indexView";
    }





}

