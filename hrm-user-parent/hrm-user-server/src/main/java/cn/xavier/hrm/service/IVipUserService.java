package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.VipUser;
import cn.xavier.hrm.dto.RegisterDto;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-25
 */
public interface IVipUserService extends IService<VipUser> {

    AjaxResult register(RegisterDto dto);
}
