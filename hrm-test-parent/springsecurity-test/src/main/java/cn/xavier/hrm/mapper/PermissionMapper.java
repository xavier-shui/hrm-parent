package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-26
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("SELECT t3.* FROM t_user_role AS t1 JOIN t_role_permission AS t2 ON t1.role_id = t2.role_id JOIN t_permission AS t3 ON t2.permission_id = t3.id WHERE t1.user_id = #{id}")
    List<Permission> loadUserPermissions(Long id);
}
