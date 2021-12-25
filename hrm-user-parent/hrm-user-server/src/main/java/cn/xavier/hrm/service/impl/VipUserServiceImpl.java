package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.LoginUserTypeConstant;
import cn.xavier.hrm.constant.RedisKeyConstant;
import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.VipUser;
import cn.xavier.hrm.dto.RegisterDto;
import cn.xavier.hrm.feign.IRegisterFeignClient;
import cn.xavier.hrm.mapper.VipUserMapper;
import cn.xavier.hrm.service.IVipUserService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.BitStatesUtils;
import cn.xavier.hrm.util.RedisUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-25
 */
@Service
public class VipUserServiceImpl extends ServiceImpl<VipUserMapper, VipUser> implements IVipUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IRegisterFeignClient feignClient;

    @Autowired
    private VipUserMapper vipUserMapper;

    @Override
    public AjaxResult register(RegisterDto dto) {
        String mobile = dto.getMobile();
        // 短信验证码校验
        redisUtil.verifyCodeCheck(RedisKeyConstant.USER_REGISTER_PREFIX + mobile, dto.getSmsCode());

        // 调用auth服务，保存t_login_user，返回id
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(mobile);
        loginUser.setPassword(dto.getPassword());
        AjaxResult result = feignClient.register(loginUser);
        // 如果调用成功，保存vipUser
        if (result.isSuccess()) {
            String loginId = result.getResultObj().toString();

            VipUser vipUser = new VipUser();
            // 位状态， 注册， 激活， 手机验证
            long state = BitStatesUtils.OP_REGISTED;
            state = BitStatesUtils.addState(state, BitStatesUtils.OP_ACTIVED);
            state = BitStatesUtils.addState(state, BitStatesUtils.OP_AUTHED_PHONE);
            vipUser.setBitState(state);

            vipUser.setCreateTime(System.currentTimeMillis());
            vipUser.setLoginId(Long.valueOf(loginId));
            vipUser.setPassword(dto.getPassword());
            vipUser.setPhone(mobile);
            vipUserMapper.insert(vipUser);
        }
        return result;
    }
}
