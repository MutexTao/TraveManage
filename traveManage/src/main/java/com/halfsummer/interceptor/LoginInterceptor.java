package com.halfsummer.interceptor;

import com.halfsummer.common.Const;
import com.halfsummer.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 * 用于在用户访问某些需要登录才能操作的页面时，判断用户是否已经登录。
 * 在preHandle()方法中，如果用户未登录，则重定向到登录页面；如果用户已经登录，则返回true，表示可以访问该页面。
 * postHandle()和afterCompletion()方法则在请求处理完成后执行一些操作。
 */

public class LoginInterceptor implements HandlerInterceptor {


    private void isLogin(HttpSession session){

    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = (User) httpServletRequest.getSession().getAttribute(Const.ADMIN_USER);
        if (user==null){
            System.out.println("尚未登录，调到登录页面");
            httpServletResponse.sendRedirect("http://localhost:8088/adminLoginView");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }
}
