package com.chave.config;


import com.chave.shiro.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    @Autowired
    private MyRealm myRealm;

    //配置SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        //创建defaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //创建加密对象 设置相关属性
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //使用md5加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //迭代加密3次
        hashedCredentialsMatcher.setHashIterations(3);
        //将加密对象存储到myRealm中
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        //将myRealm存入defaultWebSecurityManager
        defaultWebSecurityManager.setRealm(myRealm);
        //返回
        return defaultWebSecurityManager;
    }

    //配置shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //设置不认证可访问的资源
        definition.addPathDefinition("/user/login", "anon");
        definition.addPathDefinition("/user/logout", "anon");
        definition.addPathDefinition("/user/register", "anon");
        definition.addPathDefinition("/druid/*", "anon");
        //添加登出filter
        definition.addPathDefinition("/user/logout", "logout");
        //设置需要认证访问的资源
        definition.addPathDefinition("/user/**", "authc");

        return definition;
    }

}