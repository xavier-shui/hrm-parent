package cn.xavier.hrm.mapper;

import cn.xavier.hrm.domain.Tenant;
import cn.xavier.hrm.query.TenantQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    List<Tenant> loadTenantList(TenantQuery query, Page<Tenant> page);
}
