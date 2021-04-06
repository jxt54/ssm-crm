package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SysInitListener implements ServletContextListener{
    /*
        该方法是用来监听上下文域对象的方法，当服务器启动，上下文域对象创建，
        对象创建完毕后，马上执行该方法

        event：该参数能够取得监听的对象
                监听的是什么对象，就可以通过该参数能取得什么对象
                例如现在监听的是上下文域对象，通过该参数就可以取得上下文域对象
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //System.out.println("=======上下文域对象创建了=======");
        //获取service层对象，无法采用自动注入，采用WebApplicationContextUtils反射
        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(DicService.class);

        ServletContext application = servletContextEvent.getServletContext();
        Map<String, List<DicValue>> map = dicService.getAll();
        Set<String> set = map.keySet();
        for (String key : set){
            application.setAttribute(key,map.get(key));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
