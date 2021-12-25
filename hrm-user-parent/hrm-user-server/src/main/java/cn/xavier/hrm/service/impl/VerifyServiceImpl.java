package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.RedisKeyConstant;
import cn.xavier.hrm.domain.VipUser;
import cn.xavier.hrm.dto.SmsCodeDto;
import cn.xavier.hrm.exception.HrmException;
import cn.xavier.hrm.mapper.VipUserMapper;
import cn.xavier.hrm.service.IVerifyService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.RedisUtil;
import cn.xavier.hrm.util.ValidUtils;
import cn.xavier.hrm.util.VerifyCodeUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
@Service
public class VerifyServiceImpl implements IVerifyService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VipUserMapper vipUserMapper;

    @SneakyThrows
    @Override
    public AjaxResult imageCode(String key) {
        ValidUtils.assertNotNull(key, "key不能为空");
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        String verifyCodeBase64 = VerifyCodeUtils.getVerifyCodeBase64(100, 40, verifyCode);
        redisTemplate.opsForValue().set(key, verifyCode, 5L, TimeUnit.MINUTES);
        return AjaxResult.me().setResultObj(verifyCodeBase64);
    }

    @Override
    public AjaxResult sendSmsCode(SmsCodeDto dto) {
        // 手机号已注册校验
        VipUser vipUser = new VipUser();
        String mobile = dto.getMobile();
        vipUser.setPhone(mobile);
        // 会按照不为null的字段查
        VipUser vipUserInDb = vipUserMapper.selectOne(vipUser);
        if (vipUserInDb != null) {
            throw new HrmException("手机号已经注册，请直接登录");
        }
        // 验证码校验
        redisUtil.verifyCodeCheck(dto.getImageCodeKey(), dto.getImageCode());
        // 发送验证码
        // 可以查一下是否已发过，如果时间太短，提示用户等一下再获取。 过了重发时间可以就用旧的再发送一次
        // 这里是直接生成一个新的
        String smsCode = VerifyCodeUtils.generateVerifyCode(6, "0123456789");
        System.out.println("您的验证码是: " + smsCode);
        redisTemplate.opsForValue().set(RedisKeyConstant.USER_REGISTER_PREFIX  + mobile, smsCode, 5L, TimeUnit.MINUTES);
        return AjaxResult.me().setMessage("验证码已经发送至您的手机");
    }
}
