package com.halfsummer.service;

import com.baomidou.mybatisplus.service.IService;
import com.halfsummer.entity.EmailValidate;
import com.halfsummer.util.SendEmailUtil;

/**
 * 邮箱验证 服务类
 */
public interface IEmailValidateService extends IService<EmailValidate> {

    public boolean sendEmail(String toEmailAccount) throws Exception;

}
