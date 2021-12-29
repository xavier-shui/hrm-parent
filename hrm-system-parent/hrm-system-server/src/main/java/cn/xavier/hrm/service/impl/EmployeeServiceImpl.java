package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.mapper.EmployeeMapper;
import cn.xavier.hrm.service.IEmployeeService;
import cn.xavier.hrm.util.AjaxResult;
import cn.xavier.hrm.vo.UserInfo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public AjaxResult findUserInfoByLoginId(Long id) {
        UserInfo userInfo = employeeMapper.loadUserInfoByLoginId(id);
        return AjaxResult.me().setResultObj(userInfo);
    }
}
