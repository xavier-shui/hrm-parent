package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
public interface IEmployeeService extends IService<Employee> {

    AjaxResult findUserInfoByLoginId(Long id);
}
