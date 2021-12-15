package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.TenantType;
import cn.xavier.hrm.mapper.TenantTypeMapper;
import cn.xavier.hrm.service.ITenantTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户(机构)类型表 服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
@Service
public class TenantTypeServiceImpl extends ServiceImpl<TenantTypeMapper, TenantType> implements ITenantTypeService {

}
