package cn.xavier.hrm.userdetail;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.Permission;
import cn.xavier.hrm.mapper.LoginUserMapper;
import cn.xavier.hrm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zheng-Wei Shui
 * @date 12/26/2021
 */
@Service
public class LoginUserDetailService implements UserDetailsService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser = loginUserMapper.selectOne(loginUser);
        if (loginUser == null) {
            throw new UsernameNotFoundException("username not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = permissionMapper.loadUserPermissions(loginUser.getId());
        permissions.stream().forEach(permission -> {
            System.out.println("授权: " + permission);
            // perimission 转 SimpleGrantedAuthority
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getSn());
            authorities.add(authority);
        });
        return new User(loginUser.getUsername(), loginUser.getPassword(), authorities);
    }
}
