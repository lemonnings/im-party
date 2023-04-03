package com.im.imparty.config.security.provider;

import com.im.imparty.user.dto.UserInfoDetail;
import com.im.imparty.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements UserCache {

    @Resource
    private CacheManager cacheManager;

    @Resource
    private UserService userService;

    public MyAuthenticationProvider() {
        setUserCache(this);
        System.out.println("MyAuthenticationProvider");
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (!StringUtils.equals(userDetails.getPassword(), authentication.getCredentials().toString())) {
            throw new BadCredentialsException("密码错误");
        }
        System.out.println(userDetails);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserInfoDetail login = userService.getUserDetail(username);
        if (login == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        login.setAuthorities(login.getRoleList().stream().map(item -> (GrantedAuthority) () -> item.getRoleCode()).collect(Collectors.toList()));
        return login;
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        return cacheManager.getCache("LoginUser").get(username, UserDetails.class);
    }

    @Override
    public void putUserInCache(UserDetails user) {
        cacheManager.getCache("LoginUser").put(user.getUsername(), user);
    }

    @Override
    public void removeUserFromCache(String username) {
        cacheManager.getCache("LoginUser").evict(username);
    }
}
