package cn.xavier.hrm.userdetail;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.Permission;
import cn.xavier.hrm.mapper.LoginUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng-Wei Shui
 * @date 12/28/2021
 */
@Service
public class HrmUserDetailsService implements UserDetailsService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser = loginUserMapper.selectOne(loginUser);
        if (loginUser == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        List<Permission> permissions = loginUserMapper.loadUserPermissions(loginUser.getId());
        List<SimpleGrantedAuthority> authorities = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getSn())).collect(Collectors.toList());
        return new User(loginUser.getUsername(), loginUser.getPassword(), authorities);
    }
}
