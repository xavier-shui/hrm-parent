package cn.xavier.hrm.util;

import cn.xavier.hrm.exception.HrmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Zheng-Wei Shui
 * @date 11/25/2021
 */
@Component
public class RedisUtil {

    @Autowired //如果此类不受Spring管理，不能直接注入获取bean, 且写static无法注入
    private RedisTemplate redisTemplate;

    /**
     * 验证码校验
     * Verify code check *
     *
     * @param businessKey  business key 业务键
     * @param codeProvided code provided 客户提供的验证码
     * @throws HrmException 验证码已失效或有误抛此异常
     */
    public void verifyCodeCheck(String businessKey, String codeProvided) throws HrmException {
        Object verifyCode = redisTemplate.opsForValue().get(businessKey);
        if (verifyCode == null) {
            throw new HrmException("验证码已失效，请重新获取!");
        }

        // 不分大小写
        if (!verifyCode.toString().equalsIgnoreCase(codeProvided)) {
            throw new HrmException("验证码有误!");
        }
    }

}