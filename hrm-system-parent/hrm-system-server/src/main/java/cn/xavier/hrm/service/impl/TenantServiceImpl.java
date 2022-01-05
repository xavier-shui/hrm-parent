package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.constant.LoginUserTypeConstant;
import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.domain.LoginUser;
import cn.xavier.hrm.domain.Tenant;
import cn.xavier.hrm.dto.SettlementDto;
import cn.xavier.hrm.feign.ILoginUserFeignClient;
import cn.xavier.hrm.mapper.EmployeeMapper;
import cn.xavier.hrm.mapper.TenantMapper;
import cn.xavier.hrm.query.TenantQuery;
import cn.xavier.hrm.service.ITenantService;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ILoginUserFeignClient client;

    @Override
    public Page<Tenant> queryTenantList(Page<Tenant> page, TenantQuery query) {
        // List<Tenant> tenantList = tenantMapper.loadTenantList(page, query);
        // query, page顺序无关, 传page是为了拦截器分页
        List<Tenant> tenantList = tenantMapper.loadTenantList(query, page);
        page.setRecords(tenantList);
        return page;
    }

    @Override
    @GlobalTransactional // 被调用方不用加此注解，但需要保证表有主键
    public AjaxResult settlement(SettlementDto dto) {
        // 保存login_user, user_meal, 以后登录时不用再调用system服务
        Employee employee = dto.getEmployee();
        // 机构管理员
        employee.setType(LoginUserTypeConstant.TENANT_ADMIN);
        LoginUser loginUser = new LoginUser();
        // username, password, type
        BeanUtils.copyProperties(employee, loginUser);
        // 密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        loginUser.setPassword(encoder.encode(loginUser.getPassword()));
        loginUser.setMealId(dto.getMealId());
        AjaxResult result = client.settlement(loginUser);
        // int i = 1 / 0; 可以回滚loginUser
        // 如果调用成功
        if (result.isSuccess()) {
            // AjaxResult里面的result已由Long变为Interger, 如果是对象会变为LinkedHashMap，因为JSON的传输过程
            Integer loginId = (Integer) result.getResultObj();
            employee.setLoginId(loginId.longValue());
            // 保存employee, tenant
            employeeMapper.insert(employee);
            Tenant tenant = dto.getTenant();
            tenant.setAdminId(employee.getId());
            tenantMapper.insert(tenant);
        }
        return result;
    }
}
