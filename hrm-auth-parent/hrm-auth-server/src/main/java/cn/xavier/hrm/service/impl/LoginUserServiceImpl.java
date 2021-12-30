package cn.xavier.hrm.service.impl;

import cn.hutool.http.HttpUtil;
import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.UserMeal;
import cn.xavier.hrm.dto.LoginDto;
import cn.xavier.hrm.dto.RefreshTokenDto;
import cn.xavier.hrm.exception.HrmException;
import cn.xavier.hrm.mapper.LoginUserMapper;
import cn.xavier.hrm.mapper.UserMealMapper;
import cn.xavier.hrm.properties.OAuth2Client;
import cn.xavier.hrm.properties.OAuth2ClientDetails;
import cn.xavier.hrm.service.ILoginUserService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.util.ValidUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Map;

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

    @Autowired
    private OAuth2Client oAuth2Client;

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

    @Override
    public AjaxResult register(LoginUser loginUser) {
        loginUserMapper.insert(loginUser);
        return AjaxResult.me().setResultObj(loginUser.getId());
    }

    @Override
    public AjaxResult login(LoginDto dto) {
        String url = "http://47.108.183.122:11002/hrm/auth/oauth/token?client_id={0}&client_secret={1}&grant_type=password&redirect_uri=http://www.zhihu.com&username={2}&password={3}";
        // BasicNameValuePair clientInfo = (dto.getType().equals(LoginUserTypeConstant.ADMIN)) ? new BasicNameValuePair("admin", "1") : new BasicNameValuePair("website", "1");
        // url = MessageFormat.format(url, clientInfo.getName(), clientInfo.getValue(), dto.getUsername(), dto.getPassword());

        // 从配置文件中读取
        OAuth2ClientDetails clientDetails = oAuth2Client.getclientDetails(dto.getType());
        ValidUtils.assertNotNull(clientDetails, "不支持的类型");
        url = MessageFormat.format(url, clientDetails.getClientId(), clientDetails.getClientSecret(), dto.getUsername(), dto.getPassword());
        // 密码模式获取token
        String tokenResult = HttpUtil.get(url);
        Map<String, String> tokenResultMap = JSON.parseObject(tokenResult, Map.class);
        if (tokenResultMap.get("access_token") == null) {
            throw new HrmException("验证失败");
        }
        return AjaxResult.me().setResultObj(tokenResultMap);
    }

    @Override
    public AjaxResult refreshToken(RefreshTokenDto dto) {
        String url = "http://47.108.183.122:11004/oauth/token?grant_type=refresh_token&refresh_token={0}&client_id={1}&client_secret={2}";
        // 从配置文件中读取
        OAuth2ClientDetails clientDetails = oAuth2Client.getclientDetails(dto.getType());
        ValidUtils.assertNotNull(clientDetails, "不支持的类型");
        url = MessageFormat.format(url, dto.getRefreshToken(), clientDetails.getClientId(), clientDetails.getClientSecret());
        // 密码模式获取token
        String tokenResult = HttpUtil.get(url);
        Map<String, String> tokenResultMap = JSON.parseObject(tokenResult, Map.class);
        if (tokenResultMap.get("access_token") == null) {
            throw new HrmException("验证失败");
        }
        return AjaxResult.me().setResultObj(tokenResultMap);
    }
}
