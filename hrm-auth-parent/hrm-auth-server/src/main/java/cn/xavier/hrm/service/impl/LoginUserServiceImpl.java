package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.UserMeal;
import cn.xavier.hrm.mapper.LoginUserMapper;
import cn.xavier.hrm.mapper.UserMealMapper;
import cn.xavier.hrm.service.ILoginUserService;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-16
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements ILoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private UserMealMapper userMealMapper;

    @Override
    public AjaxResult settlement(LoginUser loginUser) {
        //mybatisPlus 自动返回自增主键
        loginUserMapper.insert(loginUser);
        UserMeal userMeal = new UserMeal();
        Long loginId = loginUser.getId();
        userMeal.setLoginId(loginId);
        userMeal.setMealId(loginUser.getMealId());
        // userMeal.setState();
        userMealMapper.insert(userMeal);
        return AjaxResult.me().setResultObj(loginId);
    }
}
