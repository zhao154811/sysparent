package com.wenyu.oauth.security.provider;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.oauth.dao.UserAuthoritiesMapper;
import com.wenyu.oauth.dao.UserMapper;
import com.wenyu.oauth.model.OauthAuthorities;
import com.wenyu.oauth.model.OauthUserDetails;
import com.wenyu.oauth.model.User;
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

        User user = userAuthoritiesMapper.getUserWithAuthoritiesByName(userName, AuthorityType.SCOPE);
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
