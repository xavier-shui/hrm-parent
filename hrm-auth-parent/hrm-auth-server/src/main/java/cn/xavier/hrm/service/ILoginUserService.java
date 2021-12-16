package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

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
}
