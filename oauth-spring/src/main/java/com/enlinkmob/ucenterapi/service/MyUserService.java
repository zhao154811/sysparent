package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.dao.UserAuthoritiesMapper;
import com.enlinkmob.ucenterapi.dao.UserMapper;
import com.enlinkmob.ucenterapi.model.OauthAuthorities;
import com.enlinkmob.ucenterapi.model.OauthUserDetails;
import com.enlinkmob.ucenterapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("myUserService")
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthoritiesMapper userAuthoritiesMapper;

    public OauthUserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
//        User user = userMapper.selectByName(userName);

        User user = userAuthoritiesMapper.getUserWithAuthoritiesByName(userName);
        OauthUserDetails userdetail = new OauthUserDetails(user == null ? new User() : user);
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        if (user != null && user.getAuthorities() != null) {
            for (OauthAuthorities authority : user.getAuthorities()) {
                list.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
        }
        userdetail.setAuthorities(list);
        return userdetail;
    }


}
