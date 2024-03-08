package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.dao.EmailValidateMapper;
import com.halfsummer.entity.EmailValidate;
import com.halfsummer.service.IEmailValidateService;
import com.halfsummer.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *  邮箱注册码 服务实现类
 */
@Service
public class EmailValidateServiceImpl extends ServiceImpl<EmailValidateMapper, EmailValidate> implements IEmailValidateService {

    @Override
    public boolean sendEmail(String toEmailAccount) throws Exception {
        //发送邮件
        String registerCode=registerCode();
        String title="注册码为："+registerCode;
        String content="注册码为："+registerCode+",请注意查收";
        SendEmailUtil.sendMail(toEmailAccount,title,content);
        //保存验证码
        EmailValidate emailValidate=new EmailValidate();
        emailValidate.setEmail(toEmailAccount);
        emailValidate.setValidateCode(registerCode);
        emailValidate.setCreateTime(new Date());
        return emailValidate.insert();
    }


    //注册码
    public  String registerCode(){
        String numberString="";
        //四位位随机数
        int i=(int)(Math.random()*10);
        int j=(int)(Math.random()*100);
        int k=(int)(Math.random()*1000);
        int l=(int)(Math.random()*10000);
        //最高位不为0
        while (l==0) {
            k=(int)(Math.random()*10000);
        }
        numberString=""+(i+j+k+l)+"";
        return numberString;
    }

}
