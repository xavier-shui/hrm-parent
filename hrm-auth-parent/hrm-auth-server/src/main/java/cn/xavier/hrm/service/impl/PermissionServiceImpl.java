package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.Permission;
import cn.xavier.hrm.mapper.PermissionMapper;
import cn.xavier.hrm.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
