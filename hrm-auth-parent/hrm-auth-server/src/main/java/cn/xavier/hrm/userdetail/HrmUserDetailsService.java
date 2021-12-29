package cn.xavier.hrm.userdetail;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.Permission;
import cn.xavier.hrm.feign.IUserInfoFeignClient;
import cn.xavier.hrm.mapper.LoginUserMapper;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.vo.UserInfo;
import com.alibaba.fastjson.JSON;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng-Wei Shui
 * @date 12/28/2021
 */
@Service
public class HrmUserDetailsService implements UserDetailsService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser = loginUserMapper.selectOne(loginUser);
        if (loginUser == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        // 封装用户详情
        AjaxResult result = userInfoFeignClient.findUserInfoByLoginId(loginUser.getId());
        if (result.isSuccess()) {
            Object userInfo = result.getResultObj(); // LinkedHashMap
            UserInfo info = JSON.parseObject(JSONObject.toJSONString((Map) userInfo), UserInfo.class); // map直接toString不行，因为key，value会成按=分割
            redisTemplate.opsForValue().set("userInfo::" + username, info);
        }

        List<Permission> permissions = loginUserMapper.loadUserPermissions(loginUser.getId());
        List<SimpleGrantedAuthority> authorities = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getSn())).collect(Collectors.toList());
        return new User(loginUser.getUsername(), loginUser.getPassword(), authorities);
    }
}
