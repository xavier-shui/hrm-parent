package cn.xavier.hrm.service.impl;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.mapper.EmployeeMapper;
import cn.xavier.hrm.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
