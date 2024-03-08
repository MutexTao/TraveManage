package com.halfsummer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.dao.UserMapper;
import com.halfsummer.entity.User;
import com.halfsummer.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  用户 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     *
     * @param username
     * @param password
     * @return
     *
     * 这段代码实现了用户登录功能。用户输入用户名和密码，系统会查询数据库，
     * 如果找到了该用户名和密码匹配的用户，则返回一个包含用户信息的成功响应对象；
     * 否则，返回一个包含错误信息的失败响应对象。
     * 其中，使用了Mybatis-Plus提供的EntityWrapper来构造SQL语句，查询数据库中的用户信息。
     * 如果查询到了匹配的用户，则将用户对象中的密码字段设为空，防止密码泄露。最后，根据查询结果返回不同的响应对象。
     */
    @Override
    public ServerResponse logion(String username, String password) {
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.eq("username",username).eq("password",password);
        User user=new User();
        if ((user=user.selectOne(ew))!=null){
            user.setPassword("");
            return ServerResponse.createBySuccess(user);
        }else{
           return ServerResponse.createByErrorMessage("用户名或密码错误");
        }
    }


    /**
     *
     * @return
     *
     * 这段代码定义了一个名为 "getAllUser" 的方法，用于获取系统中所有用户的信息。
     * 代码首先创建了一个 EntityWrapper 对象 ew，然后创建了一个空的 User 对象的 ArrayList。
     * 接着，代码使用 EntityWrapper 对象 ew 查询数据库中所有的用户数据，并将结果存储在 ArrayList 中。最后，代码返回 ArrayList 对象。
     */
    @Override
    public List<User> getAllUser() {
        EntityWrapper<User> ew = new EntityWrapper<User>();
        List<User> userArrayList = new ArrayList<>();
        userArrayList = this.baseMapper.selectList(ew);
        return userArrayList;
    }
}
