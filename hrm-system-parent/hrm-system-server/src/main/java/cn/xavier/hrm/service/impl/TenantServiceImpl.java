package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.Tenant;
import cn.xavier.hrm.mapper.TenantMapper;
import cn.xavier.hrm.service.ITenantService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

}
