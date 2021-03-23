package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("使用拦截器");
        User user = new User();
        user = (User) request.getSession().getAttribute("user");
        if (user == null){
            System.out.println("user为null");
        }else {
            System.out.println(user.getName());
        }
        if (user != null && !"".equals(user)){
            return true;
        }
        // 重定向到登录页
        /**
         * 重定向的路径怎么写?
         *  在实际项目开发中,对于路径的使用,不论操作的是前端还是后端,应该一律使用绝对路径
         *  关于转发和重定向的路径的写法如下:
         *      转发:
         *          使用的是一种特殊的绝对路径的使用方式,这种绝对路径前面不加/项目名,这种路径也被称之为内部路径
         *          /login.jsp
         *      重定向:
         *          使用的是传统绝对路径的写法,前面必须以/项目名开头,后面跟具体的资源路径
         *          /crm/login.jsp
         *
         * 为什么使用重定向,使用请求转发不行吗?
         *  转发之后,路径会停留在老路径上,而不是跳转之后最新资源的路径
         *  我们应该在为用户跳转到登录页的同时,将浏览器的地址栏应该自动设置为当前登录页的路径
         *
         *  ${pageContext.request.contextPath}  /项目名
         *
         */
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return false;
    }
}
