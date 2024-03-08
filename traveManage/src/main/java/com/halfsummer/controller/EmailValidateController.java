package com.halfsummer.controller;


import com.halfsummer.common.ServerResponse;
import com.halfsummer.service.IEmailValidateService;
import com.halfsummer.service.impl.EmailValidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *方法内部首先对 email 进行了格式验证，然后调用 emailValidateService 的 sendEmail 方法发送电子邮件。
 * 如果 sendEmail 方法返回 true，表示验证码发送成功，则方法返回一个状态码为成功的 ServerResponse 对象；否则，返回一个状态码为错误的 ServerResponse 对象。
 */
@Controller
public class EmailValidateController {

    @Autowired
    private  IEmailValidateService emailValidateService;

    //发送验证码
    @RequestMapping("/emailValidate")
    @ResponseBody
    public ServerResponse emialRegister(String email) throws Exception {

        //对邮箱格式进行验证


        //send
        EmailValidateServiceImpl emailValidateService = new EmailValidateServiceImpl();
        boolean result = emailValidateService.sendEmail(email);
        if (result){
            return ServerResponse.createBySuccess();
        }else{
            return ServerResponse.createByError();
        }
    }


}

