package com.github.DchyService;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
public class SpringBootStartApplication extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(DchyServiceApplication.class);
    }
}
