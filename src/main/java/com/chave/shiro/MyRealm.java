package com.chave.shiro;

import com.chave.pojo.User;
import com.chave.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //自定义登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户身份信息
        String username = token.getPrincipal().toString();
        //调用业务层 获取数据库中用户信息
        User user = userService.getUserByName(username);
        //非空判断 将数据封装返回
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    "MyRealm");

            return info;
        }
        return null;
    }
}