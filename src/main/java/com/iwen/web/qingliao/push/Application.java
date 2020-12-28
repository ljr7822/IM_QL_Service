package com.iwen.web.qingliao.push;

import com.iwen.web.qingliao.push.provider.AuthRequestFilter;
import com.iwen.web.qingliao.push.provider.GsonProvider;
import com.iwen.web.qingliao.push.service.AccountService;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * @Author: iwen大大怪
 * @DateTime: 2020/11/11 12:13
 */
public class Application extends ResourceConfig {
    public Application(){
        // 注册逻辑处理的包名
        packages(AccountService.class.getPackage().getName());
        // 注册全局请求拦截器
        register(AuthRequestFilter.class);
        // 注册json转化器
        //register(JacksonJsonProvider.class);
        // 替换解析器为Gson
        register(GsonProvider.class);
        // 注册日志
        register(Logger.class);
        // 注册全局请求拦截器
        // register(AuthRequestFilter.class);
    }
}
