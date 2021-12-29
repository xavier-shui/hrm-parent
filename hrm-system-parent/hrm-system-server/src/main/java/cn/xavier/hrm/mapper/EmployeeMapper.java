package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.vo.UserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("SELECT t2.id AS tenantId, t2.company_name AS tenantName, t1.id AS userId, t1.username AS userName FROM t_employee AS t1 JOIN t_tenant AS t2 ON t1.id = t2.admin_id WHERE t1.login_id = #{id}")
    UserInfo loadUserInfoByLoginId(Long id);
}
