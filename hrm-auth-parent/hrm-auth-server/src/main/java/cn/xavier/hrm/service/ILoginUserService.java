package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.dto.LoginDto;
import cn.xavier.hrm.dto.RefreshTokenDto;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

import javax.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-16
 */
public interface ILoginUserService extends IService<LoginUser> {

    AjaxResult settlement(LoginUser loginUser);

    AjaxResult register(LoginUser loginUser);

    AjaxResult login(LoginDto dto);

    AjaxResult refreshToken(@Valid RefreshTokenDto dto);
}
