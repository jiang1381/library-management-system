package com.itheima.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 替代 web.xml：注册 Spring 和 SpringMVC 的配置类，并配置 DispatcherServlet 的映射路径
 */
public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    //Spring 容器的配置类
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    //SpringMVC 容器的配置类
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    //DispatcherServlet 的映射路径
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
