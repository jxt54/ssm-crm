package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取能够与“url-pattern”中匹配的路径，注意是完全匹配的部分，*的部分不包括。
        String servletPath = request.getServletPath();
        User user = new User();
        user = (User) request.getSession().getAttribute("user");
        /*if (user == null){
            System.out.println("user为null");
        }else {
            System.out.println(user.getName());
        }*/
        //登录请求"/login.do"和登录页面"/login.jsp"不需要过滤，直接放行
        if (servletPath.equals("/login.do") || servletPath.equals("/login.jsp")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            if (user == null || "".equals(user.getName())){
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
            }else {
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
